var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var dateFormat = "YYYY-MM-DD HH:MM:SS";
var addOrUpdateUrl = baseUrl + "/groupManage/addOrUpdate";
var delUrl = baseUrl + "/groupManage/delete";
var pagesUrl = baseUrl + "/groupManage/pages";


var groupManage = {
    query: function(){
        $(grid_selector).jqGrid('clearGridData');  //清空表格
        $(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
            postData : {
                name: $("#name").val()
            },   //  newdata 是符合格式要求的需要重新加载的数据
        }).trigger("reloadGrid");
    },
    initGridData: function(){
        jQuery(grid_selector).jqGrid({
            url: pagesUrl,
            datatype: "json",
            mtype: "post",
            postData: {},
            jsonReader: {
                root: "data.list",
                page: 10,
                total: "data.pages",
                records: "data.total"
            },
            colNames: ['ID', '名称', '描述', '创建时间'],
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
                    name: 'description',
                    index: 'description',
                    width: 150,
                    editable: true,
                    edittype: "textarea"
                }, {
                    name: 'recordTime',
                    index: 'recordTime',
                    width: 70,
                    editable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var date = new Date();
                        date.setTime(cellvalue);
                        return date.Format("yyyy-MM-dd HH:mm:ss");
                    }
                }
            ],
            viewrecords: true,
            // height: 500,
            height: 'auto',
            rowNum: 15,
            rowList: [15, 30, 50],
            pager: pager_selector,
            altRows: false,
            multiselect: true,
            multiboxonly: true,
            rownumbers: true,
            editurl: addOrUpdateUrl,//nothing is saved
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
                    $("#editmodgrid-table").css("top","20%");
                    $("#editmodgrid-table").css("left","40%");
                }
            },
            {
                //new record form
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: true,
                beforeShowForm: function (e) {
//                    var form = $(e[0]);
//                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
//                        .wrapInner('<div class="widget-header" />')
//                    style_edit_form(form);
                },
                onInitializeForm: function (formid) {
                    $(grid_selector).jqGrid('setGridParam', {editurl: addOrUpdateUrl});
                },
                afterSubmit: function (response, postdata) {
                    var res = $.parseJSON(response.responseText);
                    console.log(res);
                    console.log(postdata);
                    if (res.code != 200) {
                        return [false, res.msg];
                    }
                    else {
                        return [true];
                    }
                }
            },
            {
                //delete record form
                recreateForm: true,
                onInitializeForm: function (formid) {
                    $(grid_selector).jqGrid('setGridParam', {editurl: delUrl});
                },
                beforeShowForm: function (e) {
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
    initGirdAutoWidth: function(){
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
    init: function () {
        $(document.body).css({
            "overflow-x":"hidden"
        });
        this.initGirdAutoWidth();
        this.initGridData();
    }

}