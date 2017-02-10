package com.readboy.magicbook.utils;

import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by chan on 2016/12/23.
 *
 *  整理出的和文件解析，Json解析相关静态方法
 */

public class FileUtil {

    public static final String TAG = "-Chan-FileUtil-";

    /**
     * @aim 获取Ftm文件中数据的跳转地址
     * @param fileName 文件名
     * @param offset 跳转地址
     * @return int 获取的跳转地址
     */
    public static int readFtmFileExtDataAddress(String fileName, int offset) {
        int iExtDataAddress = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream (fileName);
            if (fis != null) {
                byte[] arrByte = new byte[128];
                if (fis.available() > 128){
                    fis.read(arrByte, 0, 128);
                    if (arrByte[0x4c] == 'f' && arrByte[0x4d] == 't'
                            && arrByte[0x4e] == 'm'
                            && (arrByte[0x4f] & 0x07) == 0x07) {
                        iExtDataAddress = ((arrByte[offset + 0] & 0x00ff) << 0)
                                + ((arrByte[offset + 1] & 0x00ff) << 8)
                                + ((arrByte[offset + 2] & 0x00ff) << 16)
                                + ((arrByte[offset + 3] & 0x00ff) << 24);
                    }
                }
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return iExtDataAddress;
    }

    /**
     * @aim 该文件路径所指文件是否存在
     * @param filepath
     *            文件全路径
     * @return true 文件存在 false 文件不存在
     */
    public static boolean bFileExists(String filepath) {
        try {
            if (filepath != null && filepath.length() > 0) {
                File f = new File (filepath);
                if (f.exists() && f.isFile()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @aim 把JSONArray转换成ArrayList
     * @param resJsonArray 源JSONArray
     * @param resArrayList 结果ArrayList
     * @return 无
     */
    public static void getJsonObjectslArrayList(JSONArray resJsonArray, ArrayList<JSONObject> resArrayList) {
        try {
            if (resJsonArray != null && resJsonArray.length() > 0){
                for (int id = 0; id < resJsonArray.length(); id++) {
                    JSONObject jsonObject = (JSONObject)resJsonArray.get(id);
                    resArrayList.add(jsonObject);
                    if (jsonObject.has("dcds")){
                        getJsonObjectslArrayList(jsonObject.getJSONArray("dcds"), resArrayList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "======getJsonObjectslArrayList===ERROR=====");
        }
    }

    /**
     * @aim 验证文件是否正确
     * @param fileName
     *            文件全路径
     * @return 是否是好的数据
     */
    public static boolean bCheckFileIsOk(String fileName) {
        boolean bFileIsOk = true;
        FileInputStream fis = null;
        try {
            if (!bFileExists(fileName)) {
                // 文件不存在
                bFileIsOk = false;
                Log.w(TAG, "=====bCheckFileIsOk=1===");
            } else {
                String strTempFile = fileName.toLowerCase();
                fis = new FileInputStream (fileName);
                if (fis != null) {
                    byte[] arrByte = new byte[128];
                    if (fis.available() > 128){
                        fis.read(arrByte, 0, 128);
                        fis.close();
                        if (strTempFile.endsWith(".ftm")) {
                            if (!(arrByte[0x4c] == 'f' && arrByte[0x4d] == 't' && arrByte[0x4e] == 'm')) {
                                // 文件验证码有误
                                bFileIsOk = false;
                                Log.w(TAG, "=====bCheckFileIsOk=2===");
                            }
                        }
                        long iCrcVaule = 0;
                        iCrcVaule += ((arrByte[127] & 0x00ff) << 24);
                        iCrcVaule += ((arrByte[126] & 0x00ff) << 16);
                        iCrcVaule += ((arrByte[125] & 0x00ff) << 8);
                        iCrcVaule += ((arrByte[124] & 0x00ff) << 0);
                        // 校验值不正确
                        bFileIsOk = ftmprd_DictGenCRC32(arrByte, 124, iCrcVaule);
                    } else {
                        bFileIsOk = false;
                        fis.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "=====bCheckFileIsOk=4===");
            try {
                // 文件处理出错啦
                bFileIsOk = false;
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Log.w(TAG, "=====bCheckFileIsOk=====" + bFileIsOk);
        return bFileIsOk;
    }


    // 获取prd格式的CRC校验值
    private static boolean ftmprd_DictGenCRC32(byte[] ptr, int len, long fileCRC) {
        int id = 0;
        byte ic = 0;
        int crc = 0;
        int[] crc_table = new int[] {
                // CRC余式表
                0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
                0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef, };

        while (id < len) {
            ic = (byte) ((crc >> 8) >> 4);
            crc <<= 4;
            crc ^= crc_table[(ic ^ ((ptr[id] & 0x00ff) / 0xF)) & 0xF];
            ic = (byte) ((crc >> 8) >> 4);
            crc <<= 4;
            crc ^= crc_table[(ic ^ ((ptr[id] & 0x00ff) & 0xF)) & 0xF];
            id++;
        }
        return fileCRC == crc;
    }

    /**
     * @aim 获得机型
     * @return String 机型
     */
    public static String getMachineModule() {
        String module = "";
        try {
            Class<Build> build_class = Build.class;
            java.lang.reflect.Field field2 = build_class.getField("MODEL");
            module = (String) field2.get(new Build ());
            int i = module.length() - 1;
            for (; i >= 0; i--) {
                char indexC = module.charAt(i);
                if (!((indexC >= 'a' && indexC <= 'z')
                        || (indexC >= 'A' && indexC <= 'Z') || (indexC >= '0' && indexC <= '9'))) {
                    break;
                }
            }
            module = module.substring(i + 1);
        } catch (Exception e) {
            module = "G12";
        }
        return module;
    }


    /**
     * @aim 读取JSON数据
     * @param fileName
     *            文件名
     * @return String JSON字段
     * @exception
     *                : String title 名称 String pages 页码 String keyps 知识点 String
     *                msv 微视频 String spk 口语练习 int unint 跳转地址 Node dcds 子节点 int
     *                level 目录层级
     *
     */
    public static String readFtmFileJsonData(String fileName) {
        String strJson = "";
        FileInputStream fis = null;
        try {
            if (!bCheckFileIsOk(fileName)){
                return strJson;
            }
            fis = new FileInputStream (fileName);
            if (fis != null) {
                byte[] arrByte = new byte[128];
                int iBitDataTag = 0, iFileLenth = 0, iJsonStart = 0, iJsonEnd = 0, idCyc = 0;
                iFileLenth = fis.available();
                if (iFileLenth > 128){
                    fis.read(arrByte, 0, 128);
                    if (arrByte[0x4c] == 'f' && arrByte[0x4d] == 't'
                            && arrByte[0x4e] == 'm' && (arrByte[0x4f] & 0x07) == 0x07) {
                        iBitDataTag = arrByte[0x4f];
                        iJsonStart = ((arrByte[0x58] & 0x00ff) << 0)
                                + ((arrByte[0x59] & 0x00ff) << 8)
                                + ((arrByte[0x5a] & 0x00ff) << 16)
                                + ((arrByte[0x5b] & 0x00ff) << 24);
                        if ((iBitDataTag & 0xf8) == 0) {
                            iJsonEnd = iFileLenth - 64;
                        } else {
                            for (idCyc = 3; idCyc < 12; idCyc++) {
                                if ((iBitDataTag & (1 << idCyc)) != 0) {
                                    idCyc = 0x50 + idCyc * 4;
                                    iJsonEnd = ((arrByte[idCyc + 0] & 0x00ff) << 0)
                                            + ((arrByte[idCyc + 1] & 0x00ff) << 8)
                                            + ((arrByte[idCyc + 2] & 0x00ff) << 16)
                                            + ((arrByte[idCyc + 3] & 0x00ff) << 24);
                                }
                            }
                        }
                        arrByte = new byte[iJsonEnd - iJsonStart];
                        fis.skip(iJsonStart - 128);
                        idCyc = fis.read(arrByte, 0, iJsonEnd - iJsonStart);
                        if (idCyc != (iJsonEnd - iJsonStart)) {
                            strJson = "";
                        } else {
                            strJson = new String (arrByte, 0, idCyc, "UTF-16LE");
                        }
                    }
                }
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return strJson;
    }

    /**
     * @aim 更新JsonArray字段
     * @param jsonArrayHost
     * @param jsonArrayClient
     * @return
     */
    public static void startUpdateBookJsonDataFild(JSONArray jsonArrayHost, JSONArray jsonArrayClient) {
        JSONObject hostJsonObject = null;
        JSONObject clientJsonObject = null;
        try {
            for (int iNumber = 0; iNumber < jsonArrayHost.length(); iNumber++) {
                hostJsonObject = (JSONObject)jsonArrayHost.get(iNumber);
                clientJsonObject = (JSONObject)jsonArrayClient.get(iNumber);
                JSONArray arrName = clientJsonObject.names();
                String keyName = null;
                for (int index = 0; index < arrName.length(); index++) {
                    keyName = (String)arrName.get(index);
                    if (!keyName.equals("dcds") && !keyName.equals("title") && !keyName.equals("pages")
                            && !keyName.equals("code") && !keyName.equals("unint")){
                        hostJsonObject.put(keyName, clientJsonObject.get(keyName));
                    }
                }
                if (hostJsonObject.has("dcds")){
                    startUpdateBookJsonDataFild(hostJsonObject.getJSONArray("dcds"), clientJsonObject.getJSONArray("dcds"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @aim 获取知识点的相关信息
     * @param jsonString
     * @param fild1
     * @param fild2
     * @return
     */
    public static String getKeypsFildsJoin(String jsonString, String fild1, String fild2, boolean bOnlyFirst) {
        try {
            JSONObject jsonObj1 = new JSONObject (jsonString);
            if (jsonObj1.has(fild1)){
                String strJsonFild1 = jsonObj1.getString(fild1);
                if (strJsonFild1.indexOf("[") == -1){
                    strJsonFild1 = String.format("[%1s]", strJsonFild1);
                }
                StringBuilder sbFilds = new StringBuilder ();
                JSONArray arrJsonFild1 = new JSONArray (strJsonFild1);
                for (int index = 0; index < arrJsonFild1.length(); index++) {
                    JSONObject jsonObjFild2 = arrJsonFild1.getJSONObject(index);
                    if (jsonObjFild2 != null && jsonObjFild2.has(fild2) && !jsonObjFild2.isNull(fild2)){
                        if (sbFilds.length() > 0){
                            sbFilds.append(";");
                        }
                        sbFilds.append(jsonObjFild2.getString(fild2));
                        if (bOnlyFirst){
                            // 只要1个则立即退出
                            index = arrJsonFild1.length();
                        }
                    }
                }
                if (sbFilds.length() > 0){
                    return sbFilds.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @aim 获取该文件对应的年级
     * @param文件全路径
     * @return 该文件的年级
     */
    public static int getFileGrade(String fileName) {
        String strJson = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream (fileName);
            if (fis != null) {
                byte[] arrByte = new byte[128];
                int iGradeValue = 0;
                if (fis.available() > 128){
                    fis.read(arrByte, 0, 128);
                    if (arrByte[0x4c] == 'f' && arrByte[0x4d] == 't'
                            && arrByte[0x4e] == 'm' && (arrByte[0x4f] & 0x07) == 0x07) {
                        iGradeValue = ((arrByte[0x54] & 0x00ff) << 0)
                                + ((arrByte[0x55] & 0x00ff) << 8)
                                + ((arrByte[0x56] & 0x00ff) << 16)
                                + ((arrByte[0x57] & 0x00ff) << 24);
                        if (iGradeValue > 256) {
                            fis.skip(iGradeValue - 128);
                            iGradeValue = fis.read(arrByte, 0, 128);
                            if (iGradeValue != -1) {
                                iGradeValue = (arrByte[0x12] & 0x0ff);
                                fis.close();
                                return iGradeValue;
                            }
                        }
                    }
                }
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * @aim 获取该文件对应的年级
     * @return 该文件的年级
     */
    public static int getFileSubject(String fileName) {
        String strJson = "";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream (fileName);
            if (fis != null) {
                byte[] arrByte = new byte[128];
                int iSubjectValue = 0;
                if (fis.available() > 128){
                    fis.read(arrByte, 0, 128);
                    if (arrByte[0x4c] == 'f' && arrByte[0x4d] == 't'
                            && arrByte[0x4e] == 'm' && (arrByte[0x4f] & 0x07) == 0x07) {
                        iSubjectValue = ((arrByte[0x54] & 0x00ff) << 0)
                                + ((arrByte[0x55] & 0x00ff) << 8)
                                + ((arrByte[0x56] & 0x00ff) << 16)
                                + ((arrByte[0x57] & 0x00ff) << 24);
                        if (iSubjectValue > 256) {
                            fis.skip(iSubjectValue - 128);
                            iSubjectValue = fis.read(arrByte, 0, 128);
                            if (iSubjectValue != -1) {
                                iSubjectValue = (arrByte[0x10] & 0x0ff);
                                fis.close();
                                return iSubjectValue;
                            }
                        }
                    }
                }
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * @@aim 判断文件后缀是否是给定的后缀
     * @param fileName
     *            文件全路径
     * @param suffix
     *            判断的后缀
     * @return true 后缀一样 false 不一样的后缀
     */
    public static boolean bCheckFileSuffix(String fileName, String suffix) {
        try {
            if (fileName != null && fileName.length() > 0
                    && suffix != null && suffix.length() > 0){
                String strFileName = fileName.toLowerCase();
                if (strFileName.endsWith(suffix)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @aim 更新当前书本的Json信息
     * @return true 成功
     * 			false 失败
     */
    public static boolean bCheckUpdateBookJsonDataEnable(JSONArray jsonArrayHost, JSONArray jsonArrayClient) {
        try {
            ArrayList<JSONObject> hostArrayList = new ArrayList<JSONObject> ();
            ArrayList<JSONObject> clientArrayList = new ArrayList<JSONObject> ();
            getJsonObjectslArrayList(jsonArrayHost, hostArrayList);
            getJsonObjectslArrayList(jsonArrayClient, clientArrayList);
            if (hostArrayList.size() > 0 && hostArrayList.size() == clientArrayList.size()){
                String hostTitle = null;
                String clientTitle = null;
                JSONObject hostJsonObject = null;
                JSONObject clientJsonObject = null;
                boolean bSameNode = true;
                for (int id = 0; id < hostArrayList.size(); id++) {
                    bSameNode = true;
                    hostJsonObject = hostArrayList.get(id);
                    clientJsonObject = clientArrayList.get(id);
                    if (hostJsonObject.has("title")){
                        hostTitle = hostJsonObject.getString("title");
                    } else {
                        bSameNode = false;
                        Log.w(TAG, "==divhee=====cmp=not=equel==1===");
                    }
                    if (clientJsonObject.has("title")){
                        clientTitle = clientJsonObject.getString("title");
                    } else {
                        bSameNode = false;
                        Log.w(TAG, "==divhee=====cmp=not=equel==2===");
                    }
                    if (!clientTitle.equals(hostTitle)){
                        Log.w(TAG, "==divhee=====cmp=not=equel==3===");
                        bSameNode = false;
                    }
                    if (hostJsonObject.has("dcds") && !clientJsonObject.has("dcds")){
                        bSameNode = false;
                        Log.w(TAG, "==divhee=====cmp=not=equel==5===");
                    }
                    if (!hostJsonObject.has("dcds") && clientJsonObject.has("dcds")){
                        bSameNode = false;
                        Log.w(TAG, "==divhee=====cmp=not=equel==6===");
                    }
                    if (bSameNode){
                        // 如果目录匹配则继续检测直到完全匹配
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                Log.w(TAG, hostArrayList.size() + "==divhee====(hostsize != clientsize)========" + clientArrayList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "==divhee=====cmp=error=====");
        }
        return false;
    }


}
