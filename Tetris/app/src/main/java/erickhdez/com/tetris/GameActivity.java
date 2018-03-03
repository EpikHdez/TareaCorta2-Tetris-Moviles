package erickhdez.com.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setUpGridLayout();
    }

    private void setUpGridLayout() {
        GridLayout playArea = findViewById(R.id.playArea);
        View view;
        ImageView imageView;

        int rowsCount = playArea.getRowCount() - 1;
        int columnsCount = playArea.getColumnCount() - 1;
        
        for(int position = 0; position < playArea.getChildCount(); position++) {
            view = playArea.getChildAt(position);

            if(view instanceof ImageView) {
                imageView = (ImageView) view;

                if(!isWall(imageView, rowsCount, columnsCount)) {
                    imageView.setImageResource(R.color.backgroung);
                }
            }
        }
    }

    private boolean isWall(ImageView imageView, int rowsCount, int columnsCount) {
        String tag = imageView.getTag().toString();
        String[] position = tag.split(",");

        int row = Integer.parseInt(position[0]);
        int column = Integer.parseInt(position[1]);

        if(row == 0 || row == rowsCount) {
            return true;
        }

        if(column == 0 || column == columnsCount) {
            return true;
        }

        return false;
    }
}
