package deployits.com.ec.avispa_t.preferencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;

import deployits.com.ec.avispa_t.R;

/**
 * Inyeccion de las preferencias que estan en los recursos a traves de un fragmento,
 * es la modalidad que no esta obsoleta
 */

public class PreferenciasFrag extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

    }
}
