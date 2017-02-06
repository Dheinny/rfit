package com.dheinny.services;

import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class LambdaShowCase {
  /*  public static void main(String... args) {
        PensamentosDePessoas pensamentosDePessoas = calcularBaseadoSeONumeroEhPar(5);
        System.out.println(pensamentosDePessoas);
    }
 */
    public static PensamentosDePessoas calcularBaseadoSeONumeroEhPar(int input) {
        PensamentosDePessoas pensamentosDePessoas = new PensamentosDePessoas();


        Consumer<String> cons = new Consumer<String>() {
            @Override
            public void accept(String s) {
                pensamentosDePessoas.setJoaoPensaAssim(s);
            }
        };

        genericSetter(input,
                1,
                2,
                //pensamentosDePessoas::setJoaoPensaAssim,
                cons,
                economizaJoao -> pensamentosDePessoas.setJoaoEconomizaAssado(economizaJoao));

        genericSetter(input,
                3,
                4,
                pensamentosDePessoas::setMariaPensaAssim,
                pensamentosDePessoas::setMariaEconomizaAssado);

        genericSetter(input,
                5,
                6,
                pensamentosDePessoas::setLeticiaPensaAssim,
                pensamentosDePessoas::setLeticiaEconomizaAssado);

        Dheinny dheinny = new Dheinny();
        genericSetter(input,
                7,
                8,
                dheinny::setPensa,
                dheinny::setEconomiza);


        List<Integer> aList = IntStream.range(1, 100).boxed()
                .map(fodaseOInput -> (int) Math.random())
                .collect(toList());

        Collections.sort(aList, (o1, o2) -> o1 >= o2 ? 1 : -1);
        return pensamentosDePessoas;
    }

    public static void genericSetter(int input,
                                     int modEconomiza,
                                     int modPensa,
                                     Consumer<String> setPensaAssim,
                                     Consumer<Integer> setEconomizaAssim) {
        boolean ehPar = input % 2 == 0;

        if (ehPar) {
            setPensaAssim.accept("Par é vida: "+ modPensa*input);
            setEconomizaAssim.accept(modEconomiza*input);
        } else {
            setPensaAssim.accept("Impar é vida: " + (modPensa+input));
            setEconomizaAssim.accept(input+modEconomiza);
        }
    }
}

@Data
class PensamentosDePessoas {
    private String JoaoPensaAssim;
    private String MariaPensaAssim;
    private String PedroPensaAssim;
    private String LeticiaPensaAssim;

    private Integer JoaoEconomizaAssado;
    private Integer MariaEconomizaAssado;
    private Integer PedroEcnomizaAssado;
    private Integer LeticiaEconomizaAssado;
}

@Data
class Dheinny {
    private String pensa;
    private Integer economiza;
}