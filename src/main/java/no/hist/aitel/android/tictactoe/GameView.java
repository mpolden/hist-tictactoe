package no.hist.aitel.android.tictactoe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {

    public static final long FPS_MS = 1000 / 2;

    public enum State {
        UNKNOWN(-3),
        WIN(-2),
        EMPTY(0),
        PLAYER1(1),
        PLAYER2(2);

        private int value;

        private State(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static State fromInt(int i) {
            for (State s : values()) {
                if (s.getValue() == i) {
                    return s;
                }
            }
            return EMPTY;
        }
    }

    private static final int MARGIN = 0;
    
    private Paint linePaint;
    private Paint bmpPaint;

    private Bitmap bmpPlayer1;
    private Bitmap bmpPlayer2;
    private Drawable drawableBg;

    private ICellListener cellListener;

    private int sxy;
    private int offsetX;
    private int offsetY;
    private int boardSize;
    private GameState[][] data;
    private int selectedCell = -1;
    private GameState selectedValue = GameState.EMPTY;
    private GameState currentPlayer = GameState.UNKNOWN;
    private GameState winner = GameState.EMPTY;
    private int winCol = -1;
    private int winRow = -1;
    private int winDiag = -1;

    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();

    public interface ICellListener {
        abstract void onCellSelected();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();

        drawableBg = getResources().getDrawable(R.drawable.lib_bg);
        setBackgroundDrawable(drawableBg);

        bmpPlayer1 = getResBitmap(R.drawable.cross);
        bmpPlayer2 = getResBitmap(R.drawable.circle);

        if (bmpPlayer1 != null) {
            srcRect.set(0, 0, bmpPlayer1.getWidth() - 1, bmpPlayer1.getHeight() - 1);
        }

        bmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint = new Paint();
        linePaint.setColor(0xFFFFFFFF);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        setCurrentPlayer(GameState.PLAYER1);
    }

    public GameState[][] getData() {
        return data;
    }

    public void setCell(int x, int y, GameState value) {
        data[x][y] = value;
        invalidate();
    }

    public void setCellListener(ICellListener cellListener) {
        this.cellListener = cellListener;
    }

    public int getSelection() {
        if (selectedValue == currentPlayer) {
            return selectedCell;
        }
        return -1;
    }

    public GameState getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(GameState player) {
        currentPlayer = player;
        selectedCell = -1;
    }

    public GameState getWinner() {
        return winner;
    }

    public void setWinner(GameState winner) {
        this.winner = winner;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
        data = new GameState[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                data[i][j] = GameState.EMPTY;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int sx = (w - 2 * MARGIN) / boardSize;
        int sy = (h - 2 * MARGIN) / boardSize;
        int size = sx < sy ? sx : sy;
        sxy = size;
        offsetX = (w - boardSize * size) / 2;
        offsetY = (h - boardSize * size) / 2;
        dstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // keep the view squared
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
                GameState v;
                if (selectedCell == k) {
                    v = selectedValue;
                } else {
                    v = data[i][j];
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int sxy = this.sxy;
            x = (x - MARGIN) / sxy;
            y = (y - MARGIN) / sxy;
            //Toast.makeText(getContext(), String.valueOf(x + boardSize * y), Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), String.valueOf(x + " " + y), Toast.LENGTH_SHORT).show();
            if (isEnabled() && x >= 0 && x < boardSize && y >= 0 & y < boardSize) {
                int cell = x + boardSize * y;
                GameState state = cell == selectedCell ? selectedValue : data[x][y];
                state = state == GameState.EMPTY ? currentPlayer : GameState.EMPTY;
                selectedCell = cell;
                selectedValue = state;
                if (data[x][y] == GameState.EMPTY) {
                    setCell(x, y, selectedValue);
                    if (currentPlayer == GameState.PLAYER1) {
                        currentPlayer = GameState.PLAYER2;
                    } else {
                        currentPlayer = GameState.PLAYER1;
                    }
                }
                if (cellListener != null) {
                    cellListener.onCellSelected();
                }
            }
            return true;
        }
        return false;
    }

    private Bitmap getResBitmap(int bmpResId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);
        if (bmp == null && isInEditMode()) {
            // BitmapFactory.decodeResource doesn't work from the rendering
            // library in Eclipse's Graphical Layout Editor. Use this workaround instead.
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
