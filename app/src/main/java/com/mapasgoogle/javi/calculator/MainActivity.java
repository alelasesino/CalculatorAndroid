package com.mapasgoogle.javi.calculator;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] numberButtonsId = {R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4,
                                     R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9,
                                     R.id.btA, R.id.btB, R.id.btC, R.id.btD, R.id.btE,
                                     R.id.btF};

    private Button[] numberButtons;

    private RadioGroup groupBase;
    private RadioButton radioBinario, radioDecimal;
    private Spinner spinnerBase;

    private TextView textViewBox;
    private Button buttonDel;
    private Button buttonAC;
    private Button buttonMM;
    private Button buttonPorc;
    private Button buttonDiv;
    private Button buttonPro;
    private Button buttonRes;
    private Button buttonSum;
    private Button buttonEqu;
    private Button buttonDec;

    private String num1 = null;
    private String num2 = null;
    private String stringResult = "0";
    private boolean nextMark = true;
    private boolean markNum2 = false;
    private String lastPressedKey = null;

    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializo el objeto que llevara toda la operacion
        operation = new Operation("0", "0", null);

        initializeComponents();

        if(savedInstanceState != null)
            restoredInstaceState(savedInstanceState);

    }

    /**
     * Inicializa todos los componentes del XML y sus eventos
     */
    private void initializeComponents() {

        textViewBox = findViewById(R.id.tvBox);
        buttonDel = findViewById(R.id.btDel);
        buttonAC = findViewById(R.id.btAC);
        buttonMM = findViewById(R.id.btMM);
        buttonPorc = findViewById(R.id.btPorc);
        buttonDiv = findViewById(R.id.btDiv);
        buttonPro = findViewById(R.id.btPro);
        buttonRes = findViewById(R.id.btRes);
        buttonSum = findViewById(R.id.btSum);
        buttonDec = findViewById(R.id.btDec);
        buttonEqu = findViewById(R.id.btEqu);

        numberButtons = new Button[(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? numberButtonsId.length-6 : numberButtonsId.length)];

        for (int i = 0; i < (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? numberButtonsId.length-6 : numberButtonsId.length); i++)
            numberButtons[i] = findViewById(numberButtonsId[i]);

        buttonAC.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
        buttonMM.setOnClickListener(this);
        buttonPorc.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonPro.setOnClickListener(this);
        buttonRes.setOnClickListener(this);
        buttonSum.setOnClickListener(this);
        buttonDec.setOnClickListener(this);
        buttonEqu.setOnClickListener(this);

        for (Button bt : numberButtons)
            bt.setOnClickListener(this::onNumberClick);

        groupBase = findViewById(R.id.groupBase);
        radioBinario = findViewById(R.id.radioBinario);
        radioDecimal = findViewById(R.id.radioDecimal);
        spinnerBase = findViewById(R.id.spinner_base);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBase.setAdapter(adapter);

        radioBinario.setOnClickListener(this::onRadioBaseClick);
        radioDecimal.setOnClickListener(this::onRadioBaseClick);

        spinnerBase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                changeState();

                if(adapter.getItem(i) != null)
                    operation.setBase(Integer.parseInt(adapter.getItem(i).toString()));

                /*if(Integer.parseInt(String.valueOf(adapterView.getItemAtPosition(i))) > 9){

                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    //Toast.makeText(this, "")

                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    /**
     * Recupera el estado de la instancia guardada
     * @param savedInstace Bundle con la instancia guardada
     */
    private void restoredInstaceState(Bundle savedInstace){

        num1 = savedInstace.getString("num1");
        num2 = savedInstace.getString("num2");
        stringResult = savedInstace.getString("stringResult");
        nextMark = savedInstace.getBoolean("nextMark");
        markNum2 = savedInstace.getBoolean("markNum2");
        lastPressedKey = savedInstace.getString("lastPressedKey");

        if(num1 != null)
            operation.setNumberOne(num1);

        if(num2 != null)
            operation.setNumberTwo(num2);

        operation.setOperation1(savedInstace.getString("operacion"));

        String txtView = savedInstace.getString("textViewBox");

        if(txtView != null)
            if(!txtView.equals(""))
                textViewBox.setText(txtView);
            else
                textViewBox.setText(stringResult);

        changeState();

    }

    /**
     * Guarda el estado de la instancia actual
     * @param outState Bundle de salida del estado
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("num1", String.valueOf(operation.getNumberOne()));
        outState.putString("num2", String.valueOf(operation.getNumberTwo()));
        outState.putString("stringResult", stringResult);
        outState.putBoolean("nextMark", nextMark);
        outState.putBoolean("markNum2", markNum2);
        outState.putString("lastPressedKey", lastPressedKey);
        outState.putString("textViewBox", textViewBox.getText().toString());
        outState.putString("operacion", operation.getOperation1());

    }

    /**
     * Evento click de los radio buttons
     * @param v Vista que generó el evento click
     */
    private void onRadioBaseClick(View v){

        switch (v.getId()){
            case R.id.radioBinario:
                spinnerBase.setSelection(0);
                break;

            case R.id.radioDecimal:
                spinnerBase.setSelection(8);
                break;
        }

        changeState();

    }

    /**
     * Cambia el estado de los componentes de la aplicacion
     */
    private void changeState() {

        if(Integer.parseInt(spinnerBase.getSelectedItem().toString()) == 10)
            groupBase.check(R.id.radioDecimal);
        else if(Integer.parseInt(spinnerBase.getSelectedItem().toString()) == 2)
            groupBase.check(R.id.radioBinario);
        else
            groupBase.clearCheck();

        for(int i = 2; i<numberButtons.length; i++)
            numberButtons[i].setEnabled(Integer.parseInt(spinnerBase.getSelectedItem().toString()) > i);

        cleanAll();
        textViewBox.setText(stringResult);

    }

    /**
     * Evento click de los botones que son numeros
     * @param v Vista del boton que lanzó el evento
     */
    private void onNumberClick(View v){

        writeNumberOperation(((Button)v).getText().toString());
        textViewBox.setText(stringResult);

    }

    /**
     * Evento click de los botones de operaciones
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btAC:
                cleanAll();
                break;
            case R.id.btDel:
                cleanCharByChar();
                break;
            case R.id.btMM:
                changeSimbol();
                break;
            case R.id.btPorc:
                configureOperation("porc", buttonPorc);
                break;
            case R.id.btDec:
                if(stringResult.equals("")) stringResult = "0";
                writeNumberOperation(",");
                break;
            case R.id.btDiv:
                configureOperation("div", buttonDiv);
                break;
            case R.id.btPro:
                configureOperation("prod", buttonPro);
                break;
            case R.id.btRes:
                configureOperation("res", buttonRes);
                break;
            case R.id.btSum:
                configureOperation("sum", buttonSum);
                break;
            default:// Boton igual
                if(operation.getOperation1() != null){
                    checkOperation();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buttonDiv.setBackground(getDrawable(R.drawable.button_secundary_no_pressed));
                    buttonPorc.setBackground(getDrawable(R.drawable.button_secundary_no_pressed));
                    buttonPro.setBackground(getDrawable(R.drawable.button_secundary_no_pressed));
                    buttonRes.setBackground(getDrawable(R.drawable.button_secundary_no_pressed));
                    buttonSum.setBackground(getDrawable(R.drawable.button_secundary_no_pressed));
                }
                markNum2 = false;
                lastPressedKey = "=";
                break;
        }

        textViewBox.setText(stringResult);
    }

    /**
     * Establece la operacion segun el estado de la calculadora
     */
    private void configureOperation(String ope, Button button) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            button.setBackground(getDrawable(R.drawable.button_secundary_pressed));

        if(lastPressedKey != ope) {
            if(lastPressedKey == "="){
                nextMark = false;
                markNum2 = true;
                operation.setOperation1(ope);
            }else {
                checkOperation();
                operation.setOperation1(ope);
                num2 = null;
                operation.setNumberTwo("0.0");
                lastPressedKey = ope;
            }
        }

    }

    /**
     * Realiza la operacion
     */
    private void checkOperation() {

        if(operation.getOperation1() != null && !operation.getNumberTwo().equals("0.0")){
            // Realizo operacion
            switch (operation.getOperation1()){
                case "sum":
                    stringResult = operation.operationSum(String.valueOf(operation.getNumberOne()), String.valueOf(operation.getNumberTwo()));
                    break;
                case "res":
                    //stringResult = String.valueOf(operation.operationDeduct(operation.getNumberOne(), operation.getNumberTwo()));
                    break;
                case "prod":
                    //stringResult = String.valueOf(operation.operationProduct(operation.getNumberOne(), operation.getNumberTwo()));
                    break;
                case "div":
                    //stringResult = String.valueOf(operation.operationDivision(operation.getNumberOne(), operation.getNumberTwo()));
                    break;
                case "porc":
                    //stringResult = String.valueOf(operation.operationPorcentage(operation.getNumberOne(), operation.getNumberTwo()));
                    break;
            }
            if(stringResult.substring(stringResult.length()-1, stringResult.length()).equals("0") &&
                    stringResult.substring(stringResult.length()-2, stringResult.length()-1).equals(".")){
                stringResult = stringResult.replace(".0", "");
            }
        }

        if(stringResult.indexOf(".") > -1 && stringResult.length() > 1) {
            deleteCeros();
        }

        num1 = stringResult;
        operation.setNumberOne(num1);
        nextMark = false;
        markNum2 = true;

        if(lastPressedKey != null){
            markNum2 = false;
            if(!lastPressedKey.equals("=")){
                num2 = null;
            }
        }
    }

    /**
     * Elimina ceros que puede dar el double a partir de la coma
     */
    private void deleteCeros() {

        for(int i = stringResult.length()-1 ; i > stringResult.indexOf(".") ; i--){
            String n = String.valueOf(stringResult.charAt(i));
            if(n.equals("0")){
                stringResult = stringResult.substring(0, i);
                i--;
            }
        }

        if(stringResult.length() > 1 &&
                stringResult.substring(stringResult.length()-1, stringResult.length()).equals(".")){
            stringResult = stringResult.replace(".", "");
        }

    }

    /**
     * Borra caracter a caracter
     */
    private void cleanCharByChar() {

        if(stringResult.length() <= 1)
            stringResult = "";
        else
            stringResult = stringResult.substring(0, stringResult.length()-1);

        if(stringResult.length() > 1 && stringResult.substring(stringResult.length()-1, stringResult.length()) == ",")
            stringResult = stringResult.replace(",", "");

        if(!stringResult.equals(""))
            insertNumberOperation(stringResult);

        nextMark = true;
    }

    /**
     * Escribe la tecla pulsada
     * @param s
     */
    private void writeNumberOperation(String s) {

        if(stringResult.equals("0") && !s.equals(",") || !nextMark) stringResult = "";

        if(s.equals(",")){ // Si pulsamos la coma de decimal...
            if(stringResult.indexOf(".") > -1){ // Comprobamos que no hay una coma ya
                return;
            }else{ // Si no la hay, la ponemos y concatenamos un 0 para que no falle al insertar el dato en el objeto, ya que es un double
                if(markNum2){
                    if(!operation.getNumberTwo().equals(0.0)) {
                        num2 = ".0";
                    }else{
                        num2 = "0.0";
                    }
                }else{
                    num1 = ".0";
                }
            }
        }else{ // Si pulsamos cualquier numero...
            if(markNum2){
                if(num2 != null && num2.indexOf(",") > -1){ // Comprobamos si hay coma de decimal, si la hay debemos borrar el 0 que le precede
                    num2 = num2.replace(".0", "");
                }else{
                    num2 = s;
                }
            }else{
                if(num1 != null && num1.indexOf(",") > -1){ // Comprobamos si hay coma de decimal, si la hay debemos borrar el 0 que le precede
                    num1 = num1.replace(".0", "");
                }else{
                    num1 = s;
                }
            }
        }

        if(markNum2){
            stringResult += num2;
            insertNumberOperation(stringResult);
            if(s.equals(",")) stringResult = stringResult.substring(0, stringResult.length()-1);
        }else{
            stringResult += num1;
            insertNumberOperation(stringResult);
            if(s.equals(",")) stringResult = stringResult.substring(0, stringResult.length()-1);
        }

        nextMark = true;
        lastPressedKey = null;
    }

    /**
     * Inserta el numero en el objeto Operation
     * @param num
     */
    private void insertNumberOperation(String num) {

        if(markNum2)
            operation.setNumberTwo(num);
        else
            operation.setNumberOne(num);

    }

    /**
     * Pulsa el boton AC, se ponen todas las variables como al principio
     */
    private void cleanAll() {

        num1 = null;
        num2 = null;
        stringResult = "0";
        nextMark = true;
        markNum2 = false;
        lastPressedKey = null;
        operation.setNumberOne("0");
        operation.setNumberTwo("0");
        operation.setOperation1(null);

    }

    /**
     * Cambia el simbolo a negativo o positivo
     */
    private void changeSimbol() {

        if(stringResult.equals("0") || (markNum2 && num2 == null)) stringResult = "";
        // Compruebo que signo tiene el texto de la caja
        if(stringResult.length() >= 1 && stringResult.substring(0, 1).equals("-")){
            stringResult = stringResult.replace("-", "");
        }else{
            stringResult = "-"+stringResult;
        }

        if(!stringResult.equals("") && !stringResult.equals("-")) {
            if (!operation.getNumberTwo().equals("0.0")) {
                operation.setNumberTwo(stringResult);
            } else {
                operation.setNumberOne(stringResult);
            }
        }
    }

}
