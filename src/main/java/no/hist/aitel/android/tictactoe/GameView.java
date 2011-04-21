package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Paint mLinePaint;

    private ICellListener cellListener;

    private int mSxy;
    private int mOffsetX;
    private int mOffsetY;

    private static final int MARGIN = 4;

    private final Rect mDstRect = new Rect();

    public interface ICellListener {
        abstract void onCellSelected();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();

        mLinePaint = new Paint();
        mLinePaint.setColor(0xFFFFFFFF);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int sx = (w - 2 * MARGIN) / 3;
        int sy = (h - 2 * MARGIN) / 3;

        int size = sx < sy ? sx : sy;

        mSxy = size;
        mOffsetX = (w - 3 * size) / 2;
        mOffsetY = (h - 3 * size) / 2;

        mDstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sxy = mSxy;
        int s3 = sxy * 3;
        int x7 = mOffsetX;
        int y7 = mOffsetY;

        for (int i = 0, k = sxy; i < 2; i++, k += sxy) {
            canvas.drawLine(x7, y7 + k, x7 + s3 - 1, y7 + k, mLinePaint);
            canvas.drawLine(x7 + k, y7, x7 + k, y7 + s3 - 1, mLinePaint);
        }
    }

    public void setCellListener(ICellListener cellListener) {
        this.cellListener = cellListener;
    }
}
