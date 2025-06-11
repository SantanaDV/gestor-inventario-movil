package com.wul4.paythunder.gestorInventario.fragments.users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.gestorInventario.response.UserResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;

public class UserDialog {
    public interface OnSaved { void onSaved(UserResponse u); }

    /**
     * existing == null → creación (contraseña + confirmación)
     * existing != null → edición (sin contraseña)
     */
    public UserDialog(Context ctx,
                      @Nullable UserResponse existing,
                      OnSaved listener) {
        LinearLayout lay = new LinearLayout(ctx);
        lay.setOrientation(LinearLayout.VERTICAL);
        int pad = (int)(16 * ctx.getResources().getDisplayMetrics().density);
        lay.setPadding(pad,pad,pad,pad);

        final TextInputEditText inEmail = new TextInputEditText(ctx);
        final TextInputEditText inName  = new TextInputEditText(ctx);
        final Spinner spinnerRole       = new Spinner(ctx);

        inEmail.setHint("Email");
        inName .setHint("Nombre");

        // Campos de contraseña solo si creamos
        final TextInputEditText[] inPass    = new TextInputEditText[1];
        final TextInputEditText[] inConfirm = new TextInputEditText[1];
        if (existing == null) {
            TextInputEditText pass = new TextInputEditText(ctx);
            TextInputEditText conf = new TextInputEditText(ctx);
            pass.setHint("Contraseña");
            pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            conf.setHint("Confirmar contraseña");
            conf.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            inPass[0]    = pass;
            inConfirm[0] = conf;
            lay.addView(pass);
            lay.addView(conf);
        }

        // Spinner de roles
        ArrayAdapter<String> ad = new ArrayAdapter<>(
                ctx,
                android.R.layout.simple_spinner_item,
                new String[]{"admin","empleado"}
        );
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(ad);

        // Si es edición, precargamos datos
        if (existing != null) {
            inEmail.setText(existing.getEmail());
            inName .setText(existing.getNombre());
            spinnerRole.setSelection(existing.isAdmin() ? 0 : 1);
        }

        lay.addView(inEmail);
        lay.addView(inName);
        lay.addView(spinnerRole);

        final AlertDialog dlg = new AlertDialog.Builder(ctx)
                .setTitle(existing==null ? "Crear usuario" : "Editar usuario")
                .setView(lay)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", null)  // listener se redefine
                .create();

        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialog) {
                Button ok = dlg.getButton(AlertDialog.BUTTON_POSITIVE);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        String email = inEmail.getText().toString().trim();
                        String name  = inName .getText().toString().trim();
                        String rol   = (String) spinnerRole.getSelectedItem();

                        // Validaciones
                        if (email.isEmpty() || name.isEmpty()) {
                            Toast.makeText(ctx,"Email y nombre obligatorios",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(ctx,"Email inválido",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String pass = null;
                        if (existing == null) {
                            pass = inPass[0].getText().toString();
                            String conf = inConfirm[0].getText().toString();
                            if (pass.length()<6 || !pass.matches("[A-Za-z0-9]+")) {
                                Toast.makeText(ctx,"Contraseña ≥6 alfanumérica",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (!pass.equals(conf)) {
                                Toast.makeText(ctx,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        UserResponse u = existing==null
                                ? new UserResponse()
                                : existing;
                        u.setEmail(email);
                        u.setNombre(name);
                        u.setRol(rol);

                        if (existing == null) {
                            u.setContrasena(pass);
                            // Fecha alta ahora (compatible API23)
                            String ahora = new SimpleDateFormat(
                                    "yyyy-MM-dd'T'HH:mm:ssZ",
                                    Locale.getDefault()
                            ).format(new Date());
                            u.setFechaAlta(ahora);
                            u.setEstado(1);
                            u.setFechaBaja(null);
                        }
                        listener.onSaved(u);
                        dlg.dismiss();
                    }
                });
            }
        });

        dlg.show();
    }
}
