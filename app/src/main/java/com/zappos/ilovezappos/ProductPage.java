package com.zappos.ilovezappos;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zappos.ilovezappos.databinding.ActivityProductPageBinding;

import java.io.InputStream;

public class ProductPage  extends AppCompatActivity{
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Result product = (Result) this.getIntent().getExtras().getSerializable("PRODUCT_RESULT");
        if (product != null) {
            ActivityProductPageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_page);
            binding.setResult(product);
            img = (ImageView) findViewById(R.id.imageView);
            String prodcutImage = product.getThumbnailImageUrl();
            StringBuilder sb = new StringBuilder().append(prodcutImage,prodcutImage.lastIndexOf("/"),prodcutImage.length());
            StringBuilder sb1 = new StringBuilder().append(prodcutImage,0,prodcutImage.lastIndexOf("/"));
            String prodImageURL = sb1.toString()+sb.toString().split("-")[0]+"-p-MULTIVIEW.jpg";
            new DownloadImageTask(img).execute(prodImageURL);
            if(!product.getPercentOff().equals("0%") && product.getPercentOff()!=null){

                ((TextView) findViewById(R.id.origPrice)).setPaintFlags(((TextView) findViewById(R.id.origPrice)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {

                ((TextView) findViewById(R.id.discount)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.origPrice)).setVisibility(View.GONE);
            }


            final FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.addToBasket);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rotateAnimation(fab);
                    Snackbar.make(view, "Product added to cart.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    //Toast.makeText(getApplicationContext(),"Product added to cart.",Toast.LENGTH_SHORT).show();
//                }

                }
            });

        }




    }

    public static void rotateAnimation(View v){
        // Create an animation instance
        final Animation an = new RotateAnimation(0, 720, v.getWidth()/2, v.getHeight()/2);
        an.setDuration(800);
        an.setFillAfter(true);
        v.clearAnimation();
        v.startAnimation(an);
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Bitmap image;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                image = null;
            }
            return image;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);

            }
        }
    }

}
