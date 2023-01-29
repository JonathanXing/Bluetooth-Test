package com.smartchannel.bluetoothtest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

public class FragmentPermissionHelper {
    public void startPermissionRequest(FragmentActivity fr, FragmentPermissionInterface fs, String manifest){
        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher, as an instance variable.
         ActivityResultLauncher<String> requestPermissionLauncher =
                fr.registerForActivityResult(new ActivityResultContracts.RequestPermission(), fs::onGranted);
        requestPermissionLauncher.launch(
                manifest);
    }
}
