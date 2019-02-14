var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var startTime = "";
var endTime = "";
var dateFormat = "YYYY-MM-DD HH:MM:SS";


var fileManage = {
    query: function () {
        $(grid_selector).jqGrid('clearGridData');  //清空表格
        $(grid_selector).jqGrid('setGridParam', {  // 重新加载数据
            postData: {
                title: $("#title").val(),
                startTime: startTime,
                endTime: endTime,
                groupName: $("#groupName").val()
            },   //  newdata 是符合格式要求的需要重新加载的数据
        }).trigger("reloadGrid");
    },
    initGroup: function(){
        $.ajax({
            url:  baseUrl + "/groupManage/getGroups",
            type: "POST",
            data: {},
            success: function (response) {
                var data = $.parseJSON(response);
                if (data.code == 200) {
                    if(data.data !=null && data.data.length>0){
                        $("#groupName").empty();
                        for (var i = 0;i<data.data.length;i++) {
                            var tmp = data.data[i];
                            $("#groupName").append("<option value='"+tmp.id+"'>"+tmp.name+"</option>")
                        }

                    }
                }
                if (data.status == "error") {
                    alert(data.msg);
                }
            },
            error: function () {
                alert("请求失败！");
            }
        });
    },
    initGridData: function () {
        var self = this;
        jQuery(grid_selector).jqGrid({
            url: baseUrl + "/fileManage/pages",
            datatype: "json",
            mtype: "post",
            postData: {},
            jsonReader: {
                root: "data.list",
                page: 10,
                total: "data.pages",
                records: "data.total"
            },
            colNames: ['ID', '名称', '标题', '大小', '类型', '分组ID', '分组名称', '访问URL', '创建时间', '记录时间', '描述', '操作', ''],
            colModel: [
                {name: 'id', index: 'id', width: 60, sorttype: "int", editable: true, edittype: 'text', hidden: true},
                {
                    name: 'name',
                    index: 'name',
                    width: 100,
                    editable: true,
                    edittype: 'text',
                    editoptions: {size: "20", maxlength: "100"}
                }, {
                    name: 'title',
                    index: 'title',
                    width: 100,
                    editable: true,
                    edittype: "text",
                    editoptions: {size: "20", maxlength: "100"}
                }, {
                    name: 'size',
                    index: 'size',
                    width: 30,
                    editable: false
                }, {
                    name: 'type',
                    index: 'type',
                    width: 30,
                    editable: false
                }, {
                    name: 'groupId',
                    index: 'groupId',
                    width: 100,
                    editable: false,
                    hidden: true
                }, {
                    name: 'groupName',
                    index: 'groupName',
                    width: 50,
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}
                }, {
                    name: 'url',
                    index: 'url',
                    width: 200,
                    editable: false
                }, {
                    name: 'createTime',
                    index: 'createTime',
                    width: 80,
                    sortable: false,
                    editable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var date = new Date();
                        date.setTime(cellvalue);
                        return date.Format("yyyy-MM-dd HH:mm:ss");
                    }
                }, {
                    name: 'recordTime',
                    index: 'recordTime',
                    width: 80,
                    sortable: false,
                    editable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var date = new Date();
                        date.setTime(cellvalue);
                        return date.Format("yyyy-MM-dd HH:mm:ss");
                    }
                }, {
                    name: 'description',
                    index: 'description',
                    width: 150,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {value: "123"}
                }, {
                    name: '', index: '', width: 80, fixed: true, sortable: false, resize: false,
                    formatter: 'actions',
                    formatoptions: {
                        keys: true,
                        delbutton: true,//disable delete button
                        delOptions: {recreateForm: true, beforeShowForm: beforeDeleteCallback},
                        editformbutton: true,
                        editOptions: {recreateForm: true, closeAfterEdit: true, beforeShowForm: beforeEditCallback}
                    }
                }, {
                    name: 'fileToUpload',
                    index: 'fileToUpload',
                    hidden: true,
                    editoptions: {enctype: "multipart/form-data"},
                    edittype: 'file',
                    // formatter: this.showPicture,
                    width: 150,
                    align: "left",
                    editable: true
                }
            ],
            viewrecords: true,
            // height: 'auto',
            height: 500,
            rowNum: 15,
            rowList: [15, 30, 50],
            pager: pager_selector,
            altRows: false,
            multiselect: true,
            multiboxonly: true,
            rownumbers: true,
            editurl: baseUrl + "/fileManage/addOrUpdateFile",//nothing is saved
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    $(grid_selector).jqGrid('setLabel', 'rn', '序号', {'text-align': 'left'}, '');
                    styleCheckbox(table);
                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            }
        });
        $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
        //navButtons
        jQuery(grid_selector).jqGrid('navGrid', pager_selector,
            { 	//navbar options
                edit: true,
                editicon: 'ace-icon fa fa-pencil blue',
                add: true,
                addicon: 'ace-icon fa fa-plus-circle purple',
                del: true,
                delicon: 'ace-icon fa fa-trash-o red',
                search: false,
                searchicon: 'ace-icon fa fa-search orange',
                refresh: true,
                refreshicon: 'ace-icon fa fa-refresh green',
                view: true,
                viewicon: 'ace-icon fa fa-search-plus grey',
            },
            {
                //edit record form
                closeAfterEdit: true,
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_edit_form(form);
                    $("#editmodgrid-table").css("top", "20%");
                    $("#editmodgrid-table").css("left", "40%");
                }
            },
            {
                //new record form
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: true,
                beforeShowForm: function (e) {
                    $("#editmodgrid-table").css("top", "20%");
                    $("#editmodgrid-table").css("left", "40%");
                    // $(grid_selector).setGridParam().hideCol("fileToUpload").trigger("reloadGrid");
                    // $(grid_selector).setGridParam().showCol("fileToUpload").trigger("reloadGrid");
//                    var form = $(e[0]);
//                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
//                        .wrapInner('<div class="widget-header" />')
//                    style_edit_form(form);
                },
                onInitializeForm: function (formid) {
                    var addUrl = baseUrl + "/fileManage/addOrUpdateFile";
                    // console.log($(grid_selector).jqGrid('getGridParam', 'editurl'));
                    $(grid_selector).jqGrid('setGridParam', {editurl: addUrl});
                    $("#tr_fileToUpload").css("display", "");
                    // console.log(formid);
                    // $(formid).attr('method', 'POST');
                    // $(formid).attr('action', '/abc');
                    // $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response, postdata) {
                    var res = $.parseJSON(response.responseText);
                    if (res.code != 200) {
                        return [false, res.msg];
                    } else {
                        self.uploadFile(postdata);
                        return [true];
                    }
                }
            },
            {
                //delete record form
                recreateForm: true,
                onInitializeForm: function (formid) {
                    var delUrl = baseUrl + "/fileManage/deleteFile";
                    $(grid_selector).jqGrid('setGridParam', {editurl: delUrl});
                },
                beforeShowForm: function (e) {
                    var delUrl = baseUrl + "/fileManage/deleteFile";
                    $(grid_selector).jqGrid('setGridParam', {editurl: delUrl});
                    var form = $(e[0]);
                    if (form.data('styled')) return false;
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                    style_delete_form(form);
                    form.data('styled', true);
                },
                onClick: function (e) {
                    alert(1);
                }
            },
            {
                //search form
                recreateForm: true,
                afterShowSearch: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                    style_search_form(form);
                },
                afterRedraw: function () {
                    style_search_filters($(this));
                },
                multipleSearch: true,
            },
            {
                //view record form
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                }
            }
        )

        function beforeDeleteCallback(e) {
            var form = $(e[0]);
            if (form.data('styled')) return false;
            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_delete_form(form);
            form.data('styled', true);
            var delUrl = baseUrl + "/fileManage/deleteFile";
            $(grid_selector).jqGrid('setGridParam', {editurl: delUrl});
        }

        function beforeEditCallback(e) {
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_edit_form(form);
            var addUrl = baseUrl + "/fileManage/addOrUpdateFile";
            $(grid_selector).jqGrid('setGridParam', {editurl: addUrl});
        }
    },
    uploadFile : function(postdata){
        var formData = new FormData();
        formData.append("fileToUpload", document.getElementById("fileToUpload").files[0]);
        formData.append("name",postdata.name);
        formData.append("title",postdata.title);
        formData.append("groupName",postdata.groupName);
        formData.append("description",postdata.description);
        $.ajax({
            url: './uploadFile',
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(">>>" + data);
                if (data.status == "true") {
                    alert("上传成功！");
                    $(grid_selector).jqGrid('clearGridData');  //清空表格
                    $(grid_selector).jqGrid('setGridParam',{}).trigger("reloadGrid");
                }
                if (data.status == "error") {
                    alert(data.msg);
                }
            },
            error: function () {
                alert("上传失败！");
            }
        });
    },
    showPicture: function (cellvalue, options, rowObject) {
        // console.log("cellvalue:"+cellvalue);
        // return "<img src='" + cellvalue + "' height='50' width='50' />";
    },
    initGirdAutoWidth: function () {
        var parent_column = $(grid_selector).closest('[class*="col-"]');
        //resize to fit page size
        $(window).on('resize.jqGrid', function () {
            $(grid_selector).jqGrid('setGridWidth', parent_column.width());
        })
        //resize on sidebar collapse/expand
        $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
            if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
                //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
                setTimeout(function () {
                    $(grid_selector).jqGrid('setGridWidth', parent_column.width());
                }, 20);
            }
        })
    },
    initDatePicker: function () {
        $("#timeRange").val("请选择日期");
        $('input[name=date-range-picker]').daterangepicker({
            'applyClass': 'btn-sm btn-success',
            'cancelClass': 'btn-sm btn-default',
            showDropdowns: true,//当设置值为true的时候，允许年份和月份通过下拉框的形式选择 默认false
            timePicker24Hour: true,//设置小时为24小时制 默认false
            timePicker: true,//可选中时分 默认false
            autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
            locale: {
                format: dateFormat,
                separator: " 至 ",
                applyLabel: '确定',
                cancelLabel: '取消',
                customRangeLabel: "自定义"
            },
            ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '近7天': [moment().subtract(7, 'days'), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        }).on('cancel.daterangepicker', function (ev, picker) {
            $("#timeRange").val("请选择日期");
        }).on('apply.daterangepicker', function (ev, picker) {
            startTime = picker.startDate.format(dateFormat);
            endTime = picker.startDate.format(dateFormat);
            $("#timeRange").val(picker.startDate.format(dateFormat) + " 至 " + picker.endDate.format(dateFormat));
        });
    },
    init: function () {
        this.initGroup();
        this.initDatePicker();
        this.initGirdAutoWidth();
        this.initGridData();
    }

}