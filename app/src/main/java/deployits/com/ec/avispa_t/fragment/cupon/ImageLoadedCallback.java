package deployits.com.ec.avispa_t.fragment.cupon;

import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

public class ImageLoadedCallback implements Callback {
    ProgressBar progressBar;

    public ImageLoadedCallback(ProgressBar progBar) {
        progressBar = progBar;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}