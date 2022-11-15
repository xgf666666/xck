package com.example.xck.utils.filechoose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.xck.R;
import com.example.xck.common.Constants;
import com.example.xck.common.StringExKt;
import com.facebook.stetho.common.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * 自定义文件浏览器和文件对话框
 */
public class FileBrowseActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private FxFileDialogArgs _args = new FxFileDialogArgs();
    private File _currentFilePath = null;
    private ArrayList<FileItemInfo> _filelist = null;
    private ArrayList<FileItemInfo> curDirFileLists = null;
    private List<String> formatList;
    private LinearLayout layoutNoFileImage;
    private FileBrowseAdapter _adapter;
    private int _orderBy = ORDER_BY_NAME;    //排序方式，名称/大小/类型/修改时间
    private boolean _orderType = true;   //升序或是降序排列文件
//    private ArrayList<File> _copyFiles = new ArrayList<>();   //需要复制的文件列表
//    private boolean _isCopyOrCut = false;   //true复制，false剪切
//    private File _copyPath = null;		//文件复制或粘贴的文件夹

    private static final int ORDER_BY_NAME = 0;        //按名称排序方式
    private static final int ORDER_BY_SIZE = 1;        //按大小排序方式
    private static final int ORDER_BY_MODIFY = 2;    //按修改时间排序方式
    private static final int ORDER_BY_TYPE = 3;        //按类型排序方式

    private static final int SELECT_FILE_REQUESTCODE = 101;
    private LinearLayout ll_checkBox_all;
    private CheckBox checkBox_all;
    private RecyclerView recyclerViewFileName;
    private TextView textQuit;
    private ListView lv_files;
    private RelativeLayout rl_upload;
    private FileFilter _fileFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (DeviceUtil.isCustomizePhone()) {
//            Window window = getWindow();
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
//            window.setAttributes(params);
//        }
        mContext = this;
        setContentView(R.layout.fragment_file_browse);
        initView();
        initData();
    }

    private void initView() {
        layoutNoFileImage = (LinearLayout) findViewById(R.id.layout_no_file_image);
        ll_checkBox_all = (LinearLayout) findViewById(R.id.ll_checkBox_all);
        checkBox_all = (CheckBox) findViewById(R.id.checkBox_all);
        recyclerViewFileName = (RecyclerView) findViewById(R.id.recyclerView_fileName);
        textQuit = (TextView) findViewById(R.id.tv_quit);
        lv_files = (ListView) findViewById(R.id.lv_filelist);
        rl_upload = (RelativeLayout) findViewById(R.id.rl_upload);

        ll_checkBox_all.setOnClickListener(this);
        textQuit.setOnClickListener(this);
        rl_upload.setOnClickListener(this);
    }

    private void initData() {
        //解析接收传递的参数
        _args = (FxFileDialogArgs) FxHelp.changeActivityObject(this);
        if (_args == null) {
            _args = new FxFileDialogArgs();
        }
        this.setTitle(_args.DialogTitle);   //设置标题
        explainFilter();
        if (StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.UPLOADFILEPATH))){
            _currentFilePath=new File("/./sdcard");
        }else {
            _currentFilePath = new File(SPUtils.getInstance().getString(Constants.UPLOADFILEPATH)); //默认为root/sdcard目录
        }
        if (!_currentFilePath.exists()) {
            _currentFilePath=new File("/./sdcard");
        }
        if (!StringUtils.isEmpty(_args.InitPath)) {
            File file = new File(_args.InitPath);
            if (file.isDirectory() && file.exists()) {
                gotoPath(file);
            } else {
                gotoPath(_currentFilePath);
            }
        } else {
            gotoPath(_currentFilePath);
        }
        refreshListView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_checkBox_all://全选按钮
                if (checkBox_all.isChecked()) {
                    _adapter.setAllItemChecked(lv_files, false);
                    checkBox_all.setChecked(false);
                } else {
                    _adapter.setAllItemChecked(lv_files, true);
                    checkBox_all.setChecked(true);
                }
                break;
          /*  case R.id.linearLayout_search://搜索按钮
                startActivityForResult(new Intent(mContext, SearchFileActivity.class), SELECT_FILE_REQUESTCODE);
                break;*/
            case R.id.tv_quit://退出按钮
                clickOKButton(0);
                break;
            case R.id.rl_upload://上传文件按钮
                ArrayList<File> ls;
                if (_adapter != null) {
                    ls = _adapter.getItemChecked();
                } else {
                    ls = new ArrayList<>();
                }

                if (ls.size() == 0) {
                    FxHelp.toast(mContext, "当前未选择任何文件!");
                } else {
                    clickOKButton(1);
                }

                break;
        }
    }

    /**
     * 跳转到某个文件夹下
     */
    private void gotoPath(File f) {
        try {
            _currentFilePath = f;
            String fileExterns = FxHelp.getFileExtern(f, 0);
            setDataToAdapter(fileExterns);
            refreshListView();
            SPUtils.getInstance().put(Constants.UPLOADFILEPATH,f.getAbsolutePath());
        } catch (Exception e) {
            FxHelp.toast(mContext, "跳转路径错误!" + FxHelp.getErrorString(e));
        }
    }

    /**
     * 设置文件夹路径数据到适配器中
     */
    private void setDataToAdapter(String fileExterns) {
        if (fileExterns != null) {
            formatList = new ArrayList<>();
            final String[] formatStr = fileExterns.split("/");
            for (String mFormatStr : formatStr) {
                if (!mFormatStr.equals("")) {
                    if (mFormatStr.equals(".")) {
                        formatList.add("");
                    } else {
                        formatList.add(mFormatStr);
                    }
                }
            }
            FileRecycleViewAdapter recycleViewAdapter = new FileRecycleViewAdapter(mContext, formatList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerViewFileName.setLayoutManager(layoutManager);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //设置增加或删除条目的动画
//            recyclerViewFileName.setItemAnimator(new DefaultItemAnimator());
            recyclerViewFileName.setAdapter(recycleViewAdapter);
            recycleViewAdapter.setAdapterItemListener(new MyAdapterItemListener() {
                @Override
                public void myItemListener(int position) {
                    if (formatList != null) {
                        if (formatList.size() > 0) {
                            //切换文件夹时取消权限按钮
                            if (checkBox_all.isChecked()) {
                                checkBox_all.setChecked(false);
                            }
                            if (position == 0) {
//                                gotoPath(new File("/."));
                            } else {
                                StringBuilder builder = new StringBuilder();
                                for (int i = 1; i < formatList.size(); i++) {
                                    if (i <= position) {
                                        builder.append("/");
                                        builder.append(formatList.get(i));
                                    }
                                }
                                String s = "/." + builder.toString();
                                gotoPath(new File(s));
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        clickOKButton(0);
    }

    /**
     * 点击了确定或取消按钮，则返回主界面
     */
    private void clickOKButton(int which) {
        if (which == 1) {  //确定
            ArrayList<File> ls = _adapter.getItemChecked();
            if (_args.DialogType == FxHelp.DLG_OPEN_FILE || _args.DialogType == FxHelp.DLG_SELECT_FOLDER) {
                if (ls.size() == 0) {
                    FxHelp.toast(mContext, "当前未选择任何文件!");
                } else {
                    if (!_args.IsMultiSelect) {
                        _args.FileName = FxHelp.getFileExtern(ls.get(0), 0);
                    } else {
                        _args.FileNames = new String[ls.size()];
                        for (int i = 0; i < ls.size(); i++) {
                            _args.FileNames[i] = FxHelp.getFileExtern(ls.get(i), 0);
                        }
                    }
                    long b= ls.get(0).length();
                    int MB=1024*1024;
                    if (ls.size()==1&&b/MB>10){
                        FxHelp.toast(mContext, "请选择小于10M的文件");
                        return;
                    }
                    gotoMainActivity(true);
                }
            } else {
                gotoMainActivity(true);
            }
        } else if (which == 0) {  //取消
            gotoMainActivity(false);
        }
    }

    /**
     * 回到主界面
     */
    private void gotoMainActivity(boolean isOK) {
        _args.DialogResult = isOK ? FxHelp.DLGRES_OK : FxHelp.DLGRES_CANCEL;
        FxHelp.changeActivity(FileBrowseActivity.this, null, _args, true, 2);
    }

    /**
     * 解析过滤文件字符
     */
    private void explainFilter() {
        final String[] s = (_args.Filter == null || _args.Filter.length() == 0) ? null : _args.Filter.split(";");
        _fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!_args.IsShowHiddenFile && pathname.isHidden()) {   //是否显示隐藏文件
                    return false;
                }
                if (!pathname.isDirectory() && !pathname.isFile()) {   //既不是文件也不是文件夹
                    return false;
                }
                if (pathname.isDirectory()) {    //文件夹一定显示
                    return true;
                } else if (_args.DialogType == FxHelp.DLG_SELECT_FOLDER) {    //选择文件夹对话框不需要显示文件
                    return false;
                } else {
                    if (s == null) {   //没有文件过滤
                        return true;
                    }
                    for (String value : s) {
                        if (pathname.getName().endsWith(value)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    /**
     * 刷新文件显示列表
     */
    private void refreshListView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File[] fList = _fileFilter == null ? _currentFilePath.listFiles() : _currentFilePath.listFiles(_fileFilter);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _filelist = new ArrayList<>();
                        curDirFileLists = new ArrayList<>();
                        if (fList != null) {
                            for (File f : fList) {
                                int[] size = FxHelp.getFileInfo(f);
                                String fInfoString;
                                if (f.isDirectory()) {
                                    fInfoString = "文件夹：" + size[1] + "   " + "文件：" + size[2];
                                } else {//文件
                                   /* String[] result = new String[2];
                                    FxHelp.formatDouble(size[0], 3, "", result);
                                    fInfoString = "大小：" + result[0] + result[1] + "B";*/
                                    fInfoString = "大小：" +FxHelp.formatFileSize(size[0]);
                                }
                                FileItemInfo fi = new FileItemInfo();
                                fi.setFile(f);
                                fi.setIcon(getFileIcon(f));
                                fi.setTitle(f.getName());
                                fi.setInfo("修改时间：" + FxHelp.format(new Date(f.lastModified())) + "   " + fInfoString);
                                fi.setCheck(false);
                                if (!StringExKt.getsuffix(fi.getTitle()).equals("txt")) {
                                    _filelist.add(fi);
                                }
                                if (!f.isDirectory()) {
                                    curDirFileLists.add(fi);
                                }
                            }

                            //全选按钮的显示和隐藏的处理
                            checkBox_all.setVisibility(View.VISIBLE);
                            if (_filelist.size() == 0 || curDirFileLists.size() == 0) {
                                checkBox_all.setVisibility(View.INVISIBLE);
                            }

                            //空文件夹的处理
                            if (_filelist.size() == 0) {
                                layoutNoFileImage.setVisibility(View.VISIBLE);
                                lv_files.setVisibility(View.GONE);
                                return;
                            }
                            layoutNoFileImage.setVisibility(View.GONE);
                            lv_files.setVisibility(View.VISIBLE);
                            sortFileList();   //将文件列表排序
                        }
//                        if (_adapter == null) {
                            _adapter = new FileBrowseAdapter(mContext, _checkChanged, _args, _filelist);
                            lv_files.setAdapter(_adapter);
//                             _adapter.notifyDataSetChanged();
                            _adapter.setAdapterItemListener(new MyAdapterItemListener() {
                                @Override
                                public void myItemListener(int position) {
                                    onListItemClick(position);
                                }
                            });
//                        }

                    }
                });
            }
        }).start();
    }

    /**
     * 将文件列表排序
     */
    private void sortFileList() {
        Collections.sort(_filelist, new Comparator<FileItemInfo>() {

            @Override
            public int compare(FileItemInfo lhs, FileItemInfo rhs) {
                File fl = lhs.getFile();
                File fr = rhs.getFile();
                if (fl.isDirectory() && fr.isFile()) {        //文件夹排在文件前面
                    return _orderType ? -1 : 1;
                } else if (fl.isFile() && fr.isDirectory()) {  //文件夹排在文件前面
                    return _orderType ? 1 : -1;
                } else if ((fl.isFile() && fr.isFile()) || (fl.isDirectory() && fr.isDirectory())) {
                    if (_orderBy == ORDER_BY_NAME) {   //按文件名排序
                        return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                fr.getName().compareToIgnoreCase(fl.getName());
                    } else if (_orderBy == ORDER_BY_MODIFY) {   //按最后修改日期排序
                        return _orderType ? (int) (fl.lastModified() - fr.lastModified()) :
                                (int) (fr.lastModified() - fl.lastModified());
                    } else if (_orderBy == ORDER_BY_TYPE) {    //按文件类型排序
                        if (fl.isDirectory()) {        //如果是文件夹，则按文件名排序
                            return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                    fr.getName().compareToIgnoreCase(fl.getName());
                        } else {
                            return _orderType ? FxHelp.getFileExtern(fl, 3).compareToIgnoreCase(FxHelp.getFileExtern(fr, 3)) :
                                    FxHelp.getFileExtern(fr, 3).compareToIgnoreCase(FxHelp.getFileExtern(fl, 3));
                        }
                    } else {   //按文件大小排序
                        if (fl.isDirectory()) {//如果是文件夹，则按文件名排序
                            return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                    fr.getName().compareToIgnoreCase(fl.getName());
                        } else {
                            return _orderType ? FxHelp.getFileInfo(fl)[0] - FxHelp.getFileInfo(fr)[0] :
                                    FxHelp.getFileInfo(fr)[0] - FxHelp.getFileInfo(fl)[0];
                        }
                    }
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 点击了列表中某个item
     */
    private void onListItemClick(int position) {
        FileItemInfo hs = (FileItemInfo) lv_files.getItemAtPosition(position);
        File file = hs.getFile();
        if (file.isDirectory()) {   //如果是文件夹则打开该文件夹
            //切换文件夹时取消权限按钮
            if (checkBox_all.isChecked()) {
                checkBox_all.setChecked(false);
            }
            gotoPath(file);
        } else {   //否则选择该文件
            _adapter.setItemChecked(lv_files, position, true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE_REQUESTCODE && resultCode == RESULT_OK) {
            if (data != null) {
                String currentAbsolutePath = data.getStringExtra("currentAbsolutePath");
                _currentFilePath = new File(currentAbsolutePath);
                if (!_currentFilePath.isDirectory()) {
                    currentAbsolutePath = currentAbsolutePath.substring(0, currentAbsolutePath.lastIndexOf("/"));
                    String fileName = StringExKt.getFileName(currentAbsolutePath);
                    for (int i = 0; i < _filelist.size(); i++) {
                        if (_filelist.get(i).getTitle().equals(fileName)) {
                            lv_files.setSelection(i);
                        }
                    }
                    _currentFilePath = new File(currentAbsolutePath);
                }
                gotoPath(_currentFilePath);
            }
        }
    }

    /**
     * 获取默认文件图标
     */
    private int getFileIcon(File f) {
        if (f.isDirectory()) {
            return R.mipmap.icon_folder;
        }
        String exTern = FxHelp.getFileExtern(f, 3).toLowerCase();

        if (exTern.equals(".doc") || exTern.equals(".docx")) {
            return R.mipmap.icon_doc;

        }

        if (exTern.equals(".jpg") || exTern.equals(".gif") || exTern.equals(".jpeg")) {
            return R.mipmap.icon_jpg;
        }

        if (exTern.equals(".pdf")) {
            return R.mipmap.icon_pdf;

        }

        if (exTern.equals(".png")) {
            return R.mipmap.icon_png;
        }

        if (exTern.equals(".ppt")) {
            return R.mipmap.icon_ppt;
        }

        if (exTern.equals(".mp4") || exTern.equals(".mpeg") || exTern.equals(".avi") || exTern.equals(".rm") || exTern.equals(".rmvb") || exTern.equals(".wmv")
                || exTern.equals(".mkv") || exTern.equals(".flv")) {
            return R.mipmap.icon_video;

        }

        if (exTern.equals(".xls")) {
            return R.mipmap.icon_xls;
        }
        return 0;
    }

    /**
     * 显示的复选框被选择的事件
     */
    private final IFxListener _checkChanged = new IFxListener() {
        @Override
        public Object OnTrigger(Object sender, Object... args) {
            int position = Integer.parseInt(args[0].toString());
            boolean isCheck = Boolean.parseBoolean(args[1].toString());
            _adapter.setItemChecked(lv_files, position, isCheck);
            ArrayList<File> files = _adapter.getItemChecked();
            if (files != null && curDirFileLists != null) {
                if (files.size() == curDirFileLists.size() && files.size() > 0 && curDirFileLists.size() > 0) {
                    checkBox_all.setChecked(true);
                } else {
                    checkBox_all.setChecked(false);
                }
            }
            return null;
        }
    };

}
