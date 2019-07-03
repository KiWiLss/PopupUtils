package com.kiwilss.lxkj.ximageselect.callback;

/**
 * @author : Lss kiwilss
 * @FileName: XImgSelectCallback
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-02
 * @desc : {DESCRIPTION}
 */
public interface XImgSelectCallback {
    //无相机可用
    //void noCamera();
    //没有相机权限,isShow,是否点了不再询问,点了就返回 false
    void noCamerPermission(boolean isShow);
    //获取图片的路径
    void getPicPath(String path);
}
