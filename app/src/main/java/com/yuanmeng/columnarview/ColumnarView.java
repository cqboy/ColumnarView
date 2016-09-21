package com.yuanmeng.columnarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * @Description：TODO(- 类描述：柱状报表控件 -)
 * @author：wsx
 * @email：heikepianzi@qq.com
 * @date 2016/9/21
 */

public class ColumnarView extends View {

    private List<Integer> values;
    private List<String> labels;

    private int textSize;
    private int textColor;
    private float maxStaff = 4000;

    private Paint textPaint;
    private Paint linePaint;
    private float valueWidth;
    private float mWidth;

    public ColumnarView(Context context) {
        super(context);
        init(null);
    }

    public ColumnarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColumnarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

        }
        textSize = (int) (11 * getContext().getResources().getDisplayMetrics().density);
        textColor = Color.parseColor("#999999");

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
        textPaint.setStyle(Paint.Style.FILL);// 设置填满

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#d5d5d5"));

//         labels = new ArrayList<String>();
//         labels.add("一");
//         labels.add("二");
//         labels.add("三");
//         labels.add("四");
//         labels.add("五");
//         labels.add("六");
//         labels.add("日");
//
//         values = new ArrayList<Integer>();
//         values.add(6000);
//         values.add(4999);
//         values.add(1414);
//         values.add(19);
//         values.add(141);
//         values.add(778);
//         values.add(248);
    }

    public void setValues(List<Integer> values) {
        this.values = values;
        for (int i = 0; i < values.size(); i++) {
            maxStaff = Math.max(maxStaff, values.get(i));
        }
        // 处理成1000的倍数
        maxStaff = ((maxStaff / 1000) + 1) * 1000;
        postInvalidate();
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
        postInvalidate();
    }

    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        onDrawStaff(canvas);
        onDrawLabels(canvas);
        onDrawValues(canvas);
    }

    /**
     * -- 绘制标尺
     *
     * @param canvas
     */
    private void onDrawStaff(Canvas canvas) {

        // 绘制顶部线条
        String maxStaffText = (int) maxStaff / 1000 + 1 + "k";
        float textWidth = textPaint.measureText(maxStaffText); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        mWidth = getMeasuredWidth() - textWidth - getDpValue(5);
        canvas.drawLine(0, textSize / 1.7f, mWidth, textSize / 1.7f, linePaint);

        // 绘制max标尺文本
        int margin = getDpValue(5); // 文字线条边距
        canvas.drawText(maxStaffText, mWidth + margin, textSize, textPaint);

        // 绘制底部线条
        margin = getDpValue(10);
        float lineY = getMeasuredHeight() - textSize - margin;
        canvas.drawLine(0, lineY, mWidth, lineY, linePaint);
    }

    /**
     * -- 绘制label文本
     *
     * @param canvas
     */
    private void onDrawLabels(Canvas canvas) {

        if (labels == null || labels == null)
            return;
        // 绘制底部标尺label
        float maxSize = Math.max(values.size(), labels.size());
        float minSize = Math.min(maxSize, labels.size());
        float labelWidth = mWidth / maxSize;
        for (int i = 0; i < labels.size(); i++) {
            float textX = labelWidth * (i * (maxSize - 1) / (minSize - 1))
                    + (labelWidth - textPaint.measureText(labels.get(i))) / 2;
            canvas.drawText(labels.get(i), textX, getMeasuredHeight()
                    - getDpValue(5), textPaint);
        }
    }

    /**
     * -- 绘制values值
     *
     * @param canvas
     */
    private void onDrawValues(Canvas canvas) {

        if (values == null)
            return;
        textPaint.setColor(Color.parseColor("#cccccc"));
        float maxSize = Math.max(values.size(), labels.size());
        float labelWidth = mWidth / maxSize;
        valueWidth = labelWidth / 3f;
        float startY = getMeasuredHeight() - textSize - getDpValue(10);
        float endY = textSize / 1.7f;
        // 绘制底部标尺label
        for (int i = 0; i < values.size(); i++) {
            // 取消绘画为0的数据
            if (values.get(i) == 0)
                continue;
            float x = labelWidth * i + (labelWidth - valueWidth) / 2;
            float y = endY + (startY - endY) - values.get(i) * (startY - endY)
                    / maxStaff;
            canvas.drawRect(x, y, x + valueWidth, startY, textPaint);// 长方形

            float textX = labelWidth * i
                    + (labelWidth - textPaint.measureText(values.get(i) + ""))
                    / 2;
            // textPaint.setTextSize(textPaint.getTextSize() / 1.1f);
            if (textX < 0)
                textX = 0;
            canvas.drawText(values.get(i) + "", textX, y - getDpValue(3),
                    textPaint);
        }
    }
}
