package com.example.canvas_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView {

    //global objects and variables

    GameThread thread;
    gameplay gp;
    PlayerView player;
    obstacleView obstacle;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //what happens when the user clicks.
        switch(event.getAction())
        {
           case MotionEvent.ACTION_DOWN: //get position of the press.
           if(event.getY()<(getHeight()/2)){player.action ="jump";}
           else if(event.getY()>(getHeight()/2)){player.action = "slide";}

        }

        return super.onTouchEvent(event);
    }

    //CHARACTER SPRITE ARRAY LIST PASSED TO THE PLAYER VIEW CLASS.
    ArrayList<Bitmap> playerSprites = new ArrayList<>();
    //OBSTACLE VIEW  IMAGES IN AN ARRAY LIST OF BITMAP.
    ArrayList<Bitmap> obstacleImage = new ArrayList<>();
    //IMAGES OF THE BIRD OBSTACLE
    ArrayList<Bitmap> bird_image_array = new ArrayList<>();
    //character sprites used in running.
    Bitmap R0;
    Bitmap R1;
    Bitmap R2;
    Bitmap R3;
    Bitmap R4;
    Bitmap R5;
    Bitmap R6;
    Bitmap R7;
    Bitmap R8;
    Bitmap R9;

    //Character Bitmaps used Jumping
    Bitmap J0;
    Bitmap J1;
    Bitmap J2;
    Bitmap J3;
    Bitmap J4;
    Bitmap J5;
    Bitmap J6;
    Bitmap J7;
    Bitmap J8;
    Bitmap J9;

    //character bitmaps used in sliding.
    Bitmap S0;
    Bitmap S1;
    Bitmap S2;
    Bitmap S3;
    Bitmap S4;
    Bitmap S5;
    Bitmap S6;
    Bitmap S7;
    Bitmap S8;
    Bitmap S9;

    //bitmaps used by the obstacle class
    Bitmap Obstacle1;
    Bitmap Obstacle2;
    Bitmap Obstacle3;
    Bitmap Obstacle4;
    Bitmap Obstacle5;
    Bitmap Obstacle6;
    Bitmap Obstacle7;
    Bitmap Obstacle8;
    Bitmap Obstacle9;

    //BITMAPS OF THE BIRD IMAGES
    Bitmap bird_0;
    Bitmap bird_1;
    Bitmap bird_2;
    Bitmap bird_3;
    Bitmap bird_4;
    Bitmap bird_5;




    //background coordinates and loop for movement ilussion.

    Bitmap BG;
    int Bg_x;
    int Bg_y;
    int Bg_x2;

    //ROAD DATA

    Bitmap road;
    int number_of_pics;  //pics used in road.
    int road_first_point_x = 0;

    //constructor

    public GameView(Context context,gameplay activity)
    {
        super(context);
        gp = activity;
        //giving the bitmap image values.
        //background
        Bg_x =0;
        Bg_y = -1000;
        Bg_x2 = 2560;

        BG = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        road = BitmapFactory.decodeResource(getResources(),R.drawable.r_desert_ground);

        R0 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__000);
        R1 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__001);
        R2 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__002);
        R3 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__003);
        R4 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__004);
        R5 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__005);
        R6 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__006);
        R7 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__007);
        R8 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__008);
        R9 = BitmapFactory.decodeResource(getResources(),R.drawable.r_run__009);

        //giving image values to the bitmaps used in jumping.
        J0 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__000);
        J1 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__001);
        J2 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__002);
        J3 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__003);
        J4 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__004);
        J5 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__005);
        J6 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__006);
        J7 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__007);
        J8 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__008);
        J9 = BitmapFactory.decodeResource(getResources(),R.drawable.r_jump__009);

        //giving image values to bitmaps used in sliding.

        S0 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__000);
        S1 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__001);
        S2 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__002);
        S3 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__003);
        S4 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__004);
        S5 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__005);
        S6 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__006);
        S7 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__007);
        S8 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__008);
        S9 = BitmapFactory.decodeResource(getResources(),R.drawable.r_slide__009);

        //bitmaps and images of obstacles.
        Obstacle1 = BitmapFactory.decodeResource(getResources(),R.drawable.r_stone);
        Obstacle2 = BitmapFactory.decodeResource(getResources(),R.drawable.r_stone_block);
        Obstacle3 = BitmapFactory.decodeResource(getResources(),R.drawable.r_crate);
        Obstacle4 = BitmapFactory.decodeResource(getResources(),R.drawable.r_skeleton);
        Obstacle5 = BitmapFactory.decodeResource(getResources(),R.drawable.r_cactus_1);
        Obstacle6 = BitmapFactory.decodeResource(getResources(),R.drawable.r_chest);
        Obstacle7 = BitmapFactory.decodeResource(getResources(),R.drawable.r_cactus_2);
        Obstacle8 = BitmapFactory.decodeResource(getResources(),R.drawable.r_cactus_3);
        Obstacle9 = BitmapFactory.decodeResource(getResources(),R.drawable.r_bush_2);


        //ADDING IMAGES TO BIRD BITMAPS.
        bird_0 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_000);
        bird_1 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_001);
        bird_2 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_002);
        bird_3 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_003);
        bird_4 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_004);
        bird_5 = BitmapFactory.decodeResource(getResources(),R.drawable.r_hawk_005);



        obstacleImage.add(Obstacle1);
        obstacleImage.add(Obstacle2);
        obstacleImage.add(Obstacle3);
        obstacleImage.add(Obstacle4);
        obstacleImage.add(Obstacle5);
        obstacleImage.add(Obstacle6);
        obstacleImage.add(Obstacle7);
        obstacleImage.add(Obstacle8);
        obstacleImage.add(Obstacle9);

        bird_image_array.add(bird_0);  //addong bitmaps to bird array list
        bird_image_array.add(bird_1);
        bird_image_array.add(bird_2);
        bird_image_array.add(bird_3);
        bird_image_array.add(bird_4);
        bird_image_array.add(bird_5);


        //adding bitmaps to the array list.
        playerSprites.add(R0); //ADDING RUN IMAGES
        playerSprites.add(R1);
        playerSprites.add(R2);
        playerSprites.add(R3);
        playerSprites.add(R4);
        playerSprites.add(R5);
        playerSprites.add(R6);
        playerSprites.add(R7);
        playerSprites.add(R9);

        playerSprites.add(J0); //ADDING JUMP IMAGES
        playerSprites.add(J1);
        playerSprites.add(J2);
        playerSprites.add(J3);
        playerSprites.add(J4);
        playerSprites.add(J5);
        playerSprites.add(J6);
        playerSprites.add(J7);
        playerSprites.add(J8);
        playerSprites.add(J9);

        playerSprites.add(S0); //adding slide image to bitmap array list playersprites
        playerSprites.add(S1);
        playerSprites.add(S2);
        playerSprites.add(S3);
        playerSprites.add(S4);
        playerSprites.add(S5);
        playerSprites.add(S6);
        playerSprites.add(S7);
        playerSprites.add(S8);
        playerSprites.add(S9);


        player = new PlayerView(playerSprites);
        obstacle = new obstacleView(obstacleImage,bird_image_array,thread,context);
        thread = new GameThread(obstacle,this,player,getHolder());

        thread.running = true;
        thread.start();

    }

    //GameView draw method.

    public void draw(Canvas canvas) {
        //draw  desert background

        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        //canvas.drawPaint(paint);
        //DRAW BACKGROUND 1
        canvas.drawBitmap(BG,Bg_x,Bg_y,null);


        //checking if the X OF BACKGROUND is far enough
        //image is described by width x height


        //ask fi  background 1 is past then reput it.
        if(Bg_x <= -2560)
        {
            Bg_x = Bg_x2 + 2560-10;//background one is put just after background two
        }else{Bg_x =Bg_x - 10;}

        //background 2 draw.
        canvas.drawBitmap(BG,Bg_x2,Bg_y,null);

        //draw the road player will run on.
        draw_road(canvas);


        if(Bg_x2 <= -2560)
        {
            Bg_x2= Bg_x +2560; //background two is put after background one
        }else{Bg_x2 =Bg_x2-10;}

        player.screenHeight = getHeight();
        player.screenWidth =  getWidth();
        obstacle.screen_height =getHeight();
        obstacle.screen_width = getWidth();
    }

    public void draw_road(Canvas canvas)
    {

        number_of_pics = (getWidth()/50)*2;

        for(int i= 0;i < number_of_pics ;i++)
        {
            //draw one image
            canvas.drawBitmap(road,(i*100)+road_first_point_x,getHeight()-110,null);
        }
        road_first_point_x = road_first_point_x - 10;

        if(road_first_point_x < - getWidth())
        {
            // if the road is past drawn area then it should be resetted to start point

            road_first_point_x = 0;
        }





    }




}
