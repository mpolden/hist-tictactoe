package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {

    
    private Paint linePaint;
    private Paint bmpPaint;

    private Bitmap bmpPlayer1;
    private Bitmap bmpPlayer2;

    private ICellListener cellListener;

    private int mSxy;
    private int mOffsetX;
    private int mOffsetY;
    private int boardSize = 5;

    private static final int MARGIN = 0;

    private final Rect mDstRect = new Rect();

    public interface ICellListener {
        abstract void onCellSelected();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();

        linePaint = new Paint();
        linePaint.setColor(0xFFFFFFFF);
        linePaint.setStrokeWidth(3);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int sx = (w - 2 * MARGIN) / boardSize;
        int sy = (h - 2 * MARGIN) / boardSize;

        int size = sx < sy ? sx : sy;

        mSxy = size;
        mOffsetX = (w - boardSize * size) / 2;
        mOffsetY = (h - boardSize * size) / 2;

        mDstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sxy = mSxy;
        int sn = sxy * boardSize;
        int x7 = mOffsetX;
        int y7 = mOffsetY;

        for (int i = 0, k = sxy; i < boardSize - 1; i++, k += sxy) {
            canvas.drawLine(x7, y7 + k, x7 + sn - 1, y7 + k, linePaint);
            canvas.drawLine(x7 + k, y7, x7 + k, y7 + sn - 1, linePaint);
        }
    }

    public void setCellListener(ICellListener cellListener) {
        this.cellListener = cellListener;
    }
}
