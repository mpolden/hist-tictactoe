package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;

public class GameView extends View {

    private static final int MARGIN = 0;
    private static final int LINE_STROKE_WIDTH = 5;
    private final Paint linePaint;
    private final Paint bmpPaint;
    private final Bitmap bmpPlayer1;
    private final Bitmap bmpPlayer2;
    private int sxy;
    private int offsetX;
    private int offsetY;
    private final int boardSize;
    private final GameBoard board;
    private GamePlayer selectedValue = GamePlayer.EMPTY;
    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();
    final Drawable drawableBg;

    /**
     * GameView handles the drawing of the board and keeps track of the GameBoard
     * @param context
     * @param boardSize
     * @param inRow
     */
    public GameView(Context context, int boardSize, int inRow) {
        super(context, null);
        this.boardSize = boardSize;
        this.board = new GameBoard(boardSize, inRow);
        requestFocus();
        drawableBg = getResources().getDrawable(R.drawable.boardbg);
        setBackgroundDrawable(drawableBg);
        bmpPlayer1 = getResBitmap(R.drawable.crossblue);
        bmpPlayer2 = getResBitmap(R.drawable.circlered);
        if (bmpPlayer1 != null) {
            srcRect.set(0, 0, bmpPlayer1.getWidth() - 1, bmpPlayer1.getHeight() - 1);
        }
        bmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint = new Paint();
        linePaint.setColor(0xFFFFFFFF);
        linePaint.setStrokeWidth(LINE_STROKE_WIDTH);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Gets the GameView's game board
     * @return GameBoard
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Get the n size of the game board.
     * @return Game board size
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Calculates the size and updates parameters after it
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (board == null) {
            return;
        }
        int sx = (w - 2 * MARGIN) / boardSize;
        int sy = (h - 2 * MARGIN) / boardSize;
        int size = sx < sy ? sx : sy;
        sxy = size;
        offsetX = (w - boardSize * size) / 2;
        offsetY = (h - boardSize * size) / 2;
        dstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    }

    /**
     * Gets the width and height from MeasureSpec and sets dimension thereafter
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // keep the view squared
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    /**
     * Gets sxy
     * @return sxy
     */
    public int getSxy() {
        return sxy;
    }

    /**
     * Handles the drawing of the view on canvas
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (board == null) {
            return;
        }
        super.onDraw(canvas);
        int sxy = this.sxy;
        int sn = sxy * boardSize;
        int x7 = offsetX;
        int y7 = offsetY;
        for (int i = 0, k = sxy; i < boardSize - 1; i++, k += sxy) {
            canvas.drawLine(x7, y7 + k, x7 + sn - 1, y7 + k, linePaint);
            canvas.drawLine(x7 + k, y7, x7 + k, y7 + sn - 1, linePaint);
        }
        for (int j = 0, k = 0, y = y7; j < boardSize; j++, y += sxy) {
            for (int i = 0, x = x7; i < boardSize; i++, k++, x += sxy) {
                dstRect.offsetTo(MARGIN + x, MARGIN + y);
                GamePlayer v;
                int selectedCell = -1;
                if (selectedCell == k) {
                    v = selectedValue;
                } else {
                    v = board.get(i, j);
                }
                switch (v) {
                    case PLAYER1:
                        if (bmpPlayer1 != null) {
                            canvas.drawBitmap(bmpPlayer1, srcRect, dstRect, bmpPaint);
                        }
                        break;
                    case PLAYER2:
                        if (bmpPlayer2 != null) {
                            canvas.drawBitmap(bmpPlayer2, srcRect, dstRect, bmpPaint);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Gets bitmap of given bmp src.
     * @param bmpResId
     * @return bmp
     */
    private Bitmap getResBitmap(int bmpResId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);
        if (bmp == null && isInEditMode()) {
            Drawable d = res.getDrawable(bmpResId);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            d.setBounds(0, 0, w - 1, h - 1);
            d.draw(c);
        }
        return bmp;
    }
}
