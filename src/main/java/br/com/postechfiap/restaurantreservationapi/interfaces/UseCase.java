package br.com.postechfiap.restaurantreservationapi.interfaces;

public interface UseCase<Input, Output> {

    Output execute(Input entry);
}
