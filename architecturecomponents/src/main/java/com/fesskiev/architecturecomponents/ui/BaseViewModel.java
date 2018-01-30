package com.fesskiev.architecturecomponents.ui;


import android.arch.lifecycle.ViewModel;

import com.fesskiev.architecturecomponents.R;
import com.fesskiev.architecturecomponents.utils.SnackbarMessage;

public class BaseViewModel extends ViewModel {

    private final SnackbarMessage snackbarMessage = new SnackbarMessage();


    protected void showNetworkNotAvailableError() {
        snackbarMessage.setValue(R.string.shackbar_not_internet_connection);
    }

    protected void showLoadingError(Throwable throwable) {
        throwable.printStackTrace();
        snackbarMessage.setValue(R.string.shackbar_loading_error);
    }

    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }
}
