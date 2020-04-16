package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.filme.creditos.Cast;
import com.pi.appfilme.model.filme.creditos.Creditos;
import com.pi.appfilme.model.pessoa.FilmesPessoa;
import com.pi.appfilme.model.pessoa.PessoaDetalhe;
import com.pi.appfilme.model.pessoa.Profile;
import com.pi.appfilme.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PessoaViewModel extends AndroidViewModel {
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository =  new FilmeRepository();
    private MutableLiveData<List<Cast>> mutableLiveDataCast = new MutableLiveData<>();
    public LiveData<List<Cast>> liveDataCast = mutableLiveDataCast;
    private MutableLiveData<Boolean> mutableLiveDataLoading = new MutableLiveData<>();
    public LiveData<Boolean> LiveDataBoolean = mutableLiveDataLoading;
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;

    private MutableLiveData<PessoaDetalhe> mutableLiveDataPessoa = new MutableLiveData<>();
    public LiveData<PessoaDetalhe> liveDataPessoa = mutableLiveDataPessoa;

    private MutableLiveData<List<FilmesPessoa>> mutableLiveDataFilmografia = new MutableLiveData<>();
    public LiveData<List<FilmesPessoa>> liveDataFilmografia = mutableLiveDataFilmografia;

    private MutableLiveData<List<Profile>> mutableLiveDataFoto = new MutableLiveData<>();
    public LiveData<List<Profile>> liveDataFoto = mutableLiveDataFoto;

    public PessoaViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCast(long id, String apiKey){
        disposable.add(
                repository.getCreditos(id, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                .subscribe(creditos -> { mutableLiveDataCast.setValue(creditos.getCast());
                }, throwable -> {
                    mutableLiveDataErro.setValue(throwable.getMessage());
                }));
    }

    public void getPessoa(long id, String apiKey, String language){
        disposable.add(
                repository.getPessoaDetalhe(id, apiKey, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                .subscribe(pessoaDetalhe -> {
                    mutableLiveDataPessoa.setValue(pessoaDetalhe);
                }, throwable -> mutableLiveDataErro.setValue(throwable.getMessage()))
        );
    }

    public void getFilmografia(long id, String apiKey, String language){
        disposable.add(
                repository.getFilmografia(id, apiKey, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(filmografia -> {
                            mutableLiveDataFilmografia.setValue(filmografia.getCast());
                        }, throwable -> mutableLiveDataErro.setValue(throwable.getMessage()))
        );
    }

    public void getFoto(long id, String apiKey){
        disposable.add(
                repository.getFotos(id, apiKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(fotosPessoa -> {
                            mutableLiveDataFoto.setValue(fotosPessoa.getProfiles());
                        }, throwable -> mutableLiveDataErro.setValue(throwable.getMessage()))
        );
    }


}
