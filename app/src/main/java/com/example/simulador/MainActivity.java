package com.example.simulador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    //Declaração dos componentes no Java que irão referenciar os mesmos na interface gráfica
    RadioButton rbBolsaSim, rbBolsaNao, rbRegularSim, rbRegularNao;
    CheckBox cbMovel, cbBanco3, cbMultimidia, cbComDados, cbPython, cbModelagem, cbProjeto;
    EditText editBolsa;
    TextView textResultado;
    RadioGroup rgBolsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ligação do Java com o XML
        setContentView(R.layout.activity_main);

        //Liga os RadioButtons da interface gráfica com os do java
        rbBolsaSim = findViewById(R.id.rbBolsaSim);
        rbBolsaNao = findViewById(R.id.rbBolsaNao);
        rbRegularSim = findViewById(R.id.rbRegularSim);
        rbRegularNao = findViewById(R.id.rbRegularNao);

        //Liga os CheckBoxes da interface gráfica com os do java
        cbMovel = findViewById(R.id.cbMovel);
        cbBanco3 = findViewById(R.id.cbBanco3);
        cbMultimidia = findViewById(R.id.cbMultimidia);
        cbComDados = findViewById(R.id.cbComDados);
        cbPython = findViewById(R.id.cbPython);
        cbModelagem = findViewById(R.id.cbModelagem);
        cbProjeto = findViewById(R.id.cbProjeto);

        //Liga o TextView da interface gráfica com o do java
        textResultado = findViewById(R.id.textResultado);

        //Liga o EditText da interface gráfica com o do java
        editBolsa = findViewById(R.id.editBolsa);
        //implementa o evento onKey (quando uma tecla é pressionada)
        editBolsa.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            //quando o usuario modificar algo no campo da bolsa, manda recalcular o valor final
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //verifica se o valor digitado é maior do que 100, pq se for, troca pra 100
                String strBolsa = editBolsa.getText().toString();//pega o texto com o valor da bolsa
                if(!strBolsa.isEmpty()) {//se o campo da bolsa não estiver vazio
                    double valorBolsa = Double.parseDouble(strBolsa); //converte o texto para double
                    if(valorBolsa > 100){
                        editBolsa.setText("100");
                    }
                }
                calcular();
                return false;
            }
        });

        //Liga o RadioGroup da interface gráfica com o do java
        rgBolsa = findViewById(R.id.rgBolsa);

    }

    public void calcular(){
        boolean regular = false, bolsa;
        double valorHora = 4.4;
        regular = rbRegularSim.isChecked(); //isChecked retorna True se estiver marcado e False se não estiver
        bolsa = rbBolsaSim.isChecked();

        int cargaHoraria = 0;
        if(cbMovel.isChecked()) cargaHoraria+=68;
        if(cbBanco3.isChecked()) cargaHoraria+=51;
        if(cbMultimidia.isChecked()) cargaHoraria+=51;
        if(cbComDados.isChecked()) cargaHoraria+=68;
        if(cbPython.isChecked()) cargaHoraria+=34;
        if(cbModelagem.isChecked()) cargaHoraria+=51;
        if(cbProjeto.isChecked()) cargaHoraria+=68;

        double mensalidade = cargaHoraria*valorHora;

        if(!regular) mensalidade *= 2; //se não for aluno regular, a mensalidade é o dobro

        if(bolsa){//se tiver bolsa
            String strBolsa = editBolsa.getText().toString();//pega o texto com o valor da bolsa
            if(!strBolsa.isEmpty()) {//se o campo da bolsa não estiver vazio
                double valorBolsa = Double.parseDouble(strBolsa); //converte o texto para double
                mensalidade = mensalidade - mensalidade * valorBolsa / 100; //aplica a bolsa
            }
        }

        //formata o valor
        DecimalFormat df = new DecimalFormat("#.00");
        textResultado.setText("Valor da mensalidade: R$"+df.format(mensalidade));
    }
    //evento onClick do botão Calcular
    public void calcularClick(View v){
        calcular();
    }

    //Evento onClick dos radio buttons da bolsa
    //O parâmetro View v é o elemento da interface gráfica que disparou o onClick
    //É útil quando você tem mais de um elemento invocando o mesmo método onClick
    public void bolsaClick(View v){
        RadioButton radioClicado = (RadioButton) v; //converte o v em um RadioButton, pois somente RadioButtons disparam este onClick
        //descobrir em qual radio button da bolsa foi clicado
        if(radioClicado.equals(rbBolsaSim)){//clicou no RadioButton SIM
            editBolsa.setVisibility(View.VISIBLE);//Torna o campo da bolsa visível
        }
        else{
            editBolsa.setVisibility(View.GONE);//Torna o campo da bolsa gone
            editBolsa.setText("");
        }
        //recalcula a mensalidade
        calcular();

    }

    //Evento onClick dos radio buttons do aluno regular
    public void regularClick(View v){
        RadioButton radioClicado = (RadioButton) v; //converte o v em um RadioButton, pois somente RadioButtons disparam este onClick
        //descobrir em qual radio button do aluno regular foi clicado
        if(radioClicado.equals(rbRegularSim)){//clicou no RadioButton SIM
            rgBolsa.setVisibility(View.VISIBLE);//Torna o RadioGroup da bolsa visível
        }
        else{
            rgBolsa.setVisibility(View.GONE);//Torna o RadioGroup da bolsa gone
        }

        //recalcula a mensalidade
        calcular();
    }
}