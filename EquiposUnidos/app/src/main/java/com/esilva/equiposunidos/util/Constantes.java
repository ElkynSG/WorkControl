package com.esilva.equiposunidos.util;

public class Constantes {
    public static final int version = 2;

    public static final String PER_SUPER =          "Superadministrador";
    public static final String PER_MANTE =          "Lider mantenimiento";
    public static final String PER_OPER1=           "Operador nivel 1";
    public static final String PER_OPER2 =          "Operador nivel 2";
    public static final String PER_SUPER_NIVEL1 =   "Supervisor HSEQ";
    public static final String PER_SUPER_NIVEL2 =   "Cordinador HSEQ";
    public static final String PER_AXILIAR =        "Auxiliar";

    public static final int PERFIL_NULL =           0;
    public static final int PERFIL_SUPERADMIN =     1;
    public static final int PERFIL_MANTENIMIENTO =  2;
    public static final int PERFIL_OPERARIO_1 =     3;
    public static final int PERFIL_OPERARIO_2 =     4;
    public static final int PERFIL_SUPER_NIVEL_1 =  5;
    public static final int PERFIL_SUPER_NIVEL_2 =  6;
    public static final int PERFIL_AXILIAR =        7;

    public static final String PACKAGE_FILE = "EquiposUnidos";
    public static final String FILE_REPORT = "EquiposUnidos/Reportes";
    public static final String FILE_IMAGE = "EquiposUnidos/Imagenes";


    /*   biometrica  */
    //basic event
    public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final int BASE_EVENT = 3000;
    public static final int ACTIVATE_USB_DEVICE = BASE_EVENT + 1;
    public static final int REMOVE_USB_DEVICE = BASE_EVENT + 2;
    public static final int UPDATE_DEVICE_INFO = BASE_EVENT + 3;
    public static final int REQUEST_USB_PERMISSION = BASE_EVENT + 4;
    public static final int MAKE_DELAY_1SEC = BASE_EVENT + 5;
    public static final int ADD_DEVICE = BASE_EVENT + 6;
    public static final int CLEAR_VIEW_FOR_CAPTURE = BASE_EVENT + 8;
    public static final int SET_TEXT_LOGVIEW = BASE_EVENT + 10;
    public static final int MAKE_TOAST = BASE_EVENT + 11;
    public static final int SHOW_CAPTURE_IMAGE_DEVICE = BASE_EVENT + 12;

    //menu event
    public static final int BASE_MENU_EVENT = 4000;
    public static final int FAST_MODE_MENU = BASE_MENU_EVENT + 1;
    public static final int CROP_MODE_MENU = BASE_MENU_EVENT + 2;
    public static final int SENSITIVITY_MENU = BASE_MENU_EVENT + 3;
    public static final int SECURITY_MENU = BASE_MENU_EVENT + 4;
    public static final int TIMEOUT_MENU = BASE_MENU_EVENT + 5;
    public static final int DEVICE_LFD_MENU = BASE_MENU_EVENT + 6;
    public static final int SW_LFD_MENU = BASE_MENU_EVENT + 7;
    public static final int Ext_Trigger_MENU = BASE_MENU_EVENT + 8;
    public static final int Auto_Sleep_MENU = BASE_MENU_EVENT + 9;
    public static final int Auto_Rotate_MENU = BASE_MENU_EVENT + 10;
    public static final int Detect_Core_MENU = BASE_MENU_EVENT + 11;
    public static final int Template_Quality_Ex_MENU = BASE_MENU_EVENT + 12;
    public static final int Firmware_Update_MENU = BASE_MENU_EVENT + 13;

    //File Browser Event
    public static final int BASE_FILE_BROWSER_EVENT = 5000;
    public static final int FIRMWARE_UPDATE_EVENT = BASE_FILE_BROWSER_EVENT + 1;
    public static final int LFD_FROM_IMAGE_FILE_EVENT = BASE_FILE_BROWSER_EVENT + 2;


    //Device ID
    public static final int BioMiniOC4 = 0x0406;
    public static final int BioMiniSlim = 0x0407;
    public static final int BioMiniSlim2 = 0x0408;
    public static final int BioMiniPlus2 = 0x0409;
    public static final int BioMiniSlimS = 0x0420;
    public static final int BioMiniSlim2S = 0x0421;
    public static final int BioMiniSlim3 = 0x0460;
    public static final int BioMiniSlim3HID = 0x0423;

    /***************************************************************************/

}
