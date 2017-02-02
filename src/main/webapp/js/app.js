function loadSetup() {
    var theUrl = '/rs/setup/token';
    $.ajax({
        url: theUrl,
        type: 'GET',
        dataType: 'json',
        complete: function(response, status, xhr){
            console.log(response.responseText);
            var data = jQuery.parseJSON(response.responseText);
            formatCloudSetup(data);
        }
    })
}
function formatCloudSetup(data) {
    if (data.token == "none"){
        var content = '<p class="lead">You still have not requested the token.</p>';
        content += '<p>Copy the following UUID and paste it in the cloud page.</p>';
        content += '<pre>' + data.uuid + '</pre>';
        content += '<p>Then paste here the generated token.</p>';
        content += '<form class="form-horizontal" role="form">';
        content += '  <input type="hidden" name="setup_uuid" id="cfg_setup_uuid" value="' + data.uuid + '"/>';
        content += '  <div class="form-group">';
        content += '    <label  class="col-sm-2 control-label" for="ins_token">synchronization token</label>';
        content += '    <div class="col-sm-10"><input type="text" class="form-control" id="ins_token" placeholder="paste here the token..."/>';
        content += '    </div>';
        content += '  </div>';
        content += '  <button type="submit" class="btn btn-primary" id="ins_token_button"><i class="glyphicon glyphicon-ok"></i> Save Token</button>';
        content += '</form>';
        $('#body_div').html(content);
    }
}
function saveToken() {
    console.log("=============> save token");
    var uuid = $("#cfg_setup_uuid").val();
    var theUrl = '/rs/setup/register';
    var stp_data = '{';
    stp_data += '"uuid":"' + $("#cfg_setup_uuid").val() + '",';
    stp_data += '"token":"' + $("#ins_token").val() + '"}';
    console.log(stp_data);
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: stp_data,
        contentType :   'application/json',
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            console.log("============> saved token: " + data.token);
        }
    });
}
function saveController() {
    console.log("=============> save controller");
    var theUrl = '/rs/controller/register';
    var controller_data = '{';
    controller_data += '"description":"' + $("#ctrl_descr").val() + '",';
    controller_data += '"ipAddress":"' + $("#ctrl_ip").val() + '",';
    controller_data += '"type":"' + $("#ctrl_type").val() + '"}';
    console.log(controller_data);
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: controller_data,
        contentType :   'application/json',
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            console.log("============> UUID: " + data.uuid);
            loadController(data.uuid);
        }
    });
}
function loadControllers() {
    var theUrl = '/rs/controller/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatControllerTable(data);
        }
    })
}
function formatControllerTable(data) {
    $.each(data, function (index, ctrl) {
        addControllerToTable(ctrl);
    });
}
function loadController(uuid) {
    var theUrl = '/rs/controller/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            addControllerToTable(data);
        }
    })
}
function addControllerToTable(data) {
    var content = '<div class="panel panel-success" id="' + data.uuid + '">';
    content += '<div class="panel-heading l_uuid">' + data.uuid + ' <button class="btn btn-sm btn-default pull-right removeController" data-id="' + data.uuid + '"><i class="glyphicon glyphicon-remove"></i> Delete</button></div>';
    content += '<div class="panel-body"><p class="l_desc">';
    content += data.description + '</p>';
    content += '<p class="l_other">IP: ' + data.ipAddress + '</p>';
    content += '<p class="l_other">Type: ' + data.type + '</p></div>';
    content += '</div>';
    $('#controller_list').prepend(content);
}
function deleteController(uuid) {
    var theUrl = '/rs/controller/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'DELETE',
        success: function(response, status, xhr){
            var panel_id = '#' + uuid;
            $(panel_id).remove();
        }
    });
}

/* ----------------------------------------------------------------------------------------------- */

function saveTerminal() {
    var theUrl = '/rs/terminal/register';
    var controller_data = '{';
    controller_data += '"description":"' + $("#trm_descr").val() + '",';
    controller_data += '"controllerUuid":"' + $("#trm_ctrl").val() + '",';
    controller_data += '"type":' + $("#trm_type").val() + ',';
    controller_data += '"pin":' + $("#trm_gpio").val() + '}';
    console.log(controller_data);
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: controller_data,
        contentType :   'application/json',
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            console.log("============> new terminal UUID: " + data.uuid);
            loadTerminal(data.uuid);
        }
    });
}
function loadTerminal(uuid) {
    var theUrl = '/rs/terminal/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            addTerminalToTable(data);
        }
    })
}
function addTerminalToTable(data) {
    var content = '<div class="panel panel-info" id="' + data.uuid + '">';
    content += '<div class="panel-heading l_uuid">' + data.uuid + ' <button class="btn btn-sm btn-default pull-right removeTerminal" data-id="' + data.uuid + '"><i class="glyphicon glyphicon-remove"></i> Delete</button></div>';
    content += '<div class="panel-body"><p class="l_desc">';
    content += data.description + '</p>';
    content += '<p class="l_other">Controller: ' + data.controllerUuid + '</p>';
    content += '<p class="l_other">GPIO: ' + data.pin + '</p>';
    content += '<p class="l_other">Type: ' + data.type + '</p></div>';
    content += '</div>';
    $('#terminal_list').prepend(content);
}
function deleteTerminal(uuid) {
    var theUrl = '/rs/terminal/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'DELETE',
        success: function(response, status, xhr){
            var panel_id = '#' + uuid;
            $(panel_id).remove();
        }
    });
}
function loadControllerIdAndDesription() {
    var theUrl = '/rs/controller/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatNewTerminalDialogSelectOptions(data);
        }
    })
}
function formatNewTerminalDialogSelectOptions(controllerList) {
    var content = '';
    $.each(controllerList, function (index, ctrl) {
        // <option value="e0f0a03d-f229-4bbe-b382-b4977965b531">Master</option>
        content += '<option value="';
        content += ctrl.uuid + '">';
        content += ctrl.description;
        content += '</option>';
    });
    $('#trm_ctrl').html(content);
    $('#new_terminal_dialog').modal('show');
}
function loadTerminals() {
    var theUrl = '/rs/terminal/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatTerminalTable(data);
        }
    })
}
function formatTerminalTable(data) {
    $.each(data, function (index, ctrl) {
        addTerminalToTable(ctrl);
    });
}

/* ----------------------------------------------------------------------------------------------- */

function saveZone() {
    var theUrl = '/rs/zone/register';
    var controller_data = '{';
    controller_data += '"description":"' + $("#zone_descr").val() + '"}';
    console.log(controller_data);
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: controller_data,
        contentType :   'application/json',
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            loadZone(data.uuid);
        }
    });
}
function deleteZone(uuid) {
    var theUrl = '/rs/zone/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'DELETE',
        success: function(response, status, xhr){
            var panel_id = '#' + uuid;
            $(panel_id).remove();
        }
    });
}
function loadZones() {
    var theUrl = '/rs/zone/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatZoneTable(data);
        }
    })
}
function formatZoneTable(data) {
    $.each(data, function (index, zone) {
        addZoneToTable(zone);
    });
}
function loadZone(uuid) {
    var theUrl = '/rs/zone/' + uuid;
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            addZoneToTable(data);
        }
    })
}
function addZoneToTable(data) {
    var content = '<div class="panel panel-warning" id="' + data.uuid + '">';
    content += '<div class="panel-heading l_uuid">' + data.uuid + ' <button class="btn btn-sm btn-default pull-right removeZone" data-id="' + data.uuid + '"><i class="glyphicon glyphicon-remove"></i> Delete</button></div>';
    content += '<div class="panel-body"><p class="l_desc">';
    content += data.description + '</p>';
    content += '<div class="container" id="associated_terminal_' + data.uuid + '"></div>';
    content += '<button type="button" class="btn btn-info pull-right btn-xs add_terminal_to_zone_button" data-id="' + data.uuid + '">Associate Terminal</button>';
    content += '</div>';
    content += '</div>';
    $('#zone_list').prepend(content);
    loadAssociatedTerminals(data.uuid);
}
function loadAssociatedTerminals(z_uuid) {
    var theUrl = '/rs/zone/' + z_uuid + '/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            addTerminalsToZoneTable(z_uuid, data);
        }
    })
}
function addTerminalsToZoneTable(zone_uuid, data) {
    $.each(data, function (index, zone) {
        addTerminalToZoneTable(zone_uuid,zone);
    });
}
function addTerminalToZoneTable(zone_uuid, data) {
    var content = '<p id="' + zone_uuid + '_' + data.uuid + '">';
    content += data.description;
    content += ' <a class="removeTerminalAssociation" href="javascript:void()" data-zone-id="' + zone_uuid + '" data-trm-id="' + data.uuid + '"><span class="glyphicon glyphicon-remove-circle"></span><a></p>';
    var zone_table_id = '#associated_terminal_' + zone_uuid;
    $(zone_table_id).prepend(content);
}
function deleteTerminalAssociation(zone_uuid, terminal_uuid) {
    var theUrl = '/rs/zone/' + zone_uuid + '/remove/' + terminal_uuid;
    $.ajax({
        url: theUrl,
        type: 'DELETE',
        success: function(response, status, xhr){
            var div_id = '#' + zone_uuid + '_' + terminal_uuid;
            $(div_id).remove();
        }
    });
}
function loadTerminalSelector() {
    var theUrl = '/rs/terminal/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatTerminalSelectorOptions(data);
        }
    })
}
function formatTerminalSelectorOptions(terminal_list) {
    var content = '';
    $.each(terminal_list, function (index, trm) {
        // <option value="e0f0a03d-f229-4bbe-b382-b4977965b531">Master</option>
        content += '<option value="';
        content += trm.uuid + '">';
        content += trm.description;
        content += '</option>';
    });
    $('#terminal_selector').html(content);
    $('#terminal_association_dialog').modal('show');
}
function saveAssociation() {
    var zone_uuid = $("#cfg_zone_uuid").val();
    var terminal_uuid = $("#terminal_selector").val();
    var theUrl = '/rs/zone/' + zone_uuid + '/associate/' + terminal_uuid;
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            loadAssociation(zone_uuid,terminal_uuid);
        }
    });
}
function loadAssociation(zone_uuid,terminal_uuid){
    var theUrl = '/rs/terminal/' + terminal_uuid;
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            addTerminalToZoneTable(zone_uuid,data);
        }
    })
}

/* ----------------------------------------------------------------------------------------------- */

function loadDashboard(){
    var theUrl = '/rs/zone/list';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatDashboard(data);
        }
    });
}
function formatDashboard(array_of_zones) {
    $.each(array_of_zones, function (index, zone) {
        addZoneStatusToDashboard(zone);
    });
}
function addZoneStatusToDashboard(zone_data) {
    var panel_body_id = 'panel_body_' + zone_data.uuid;
    var content = '<h4>' + zone_data.description + '</h4>';
    content += '<div class="panel panel-info">';
    content += '<div class="panel-body" id="' + panel_body_id + '">';
    content += '</div>';
    content += '</div>';

    $('#main_dashboard').prepend(content);

    $.each(zone_data.terminalUuids, function (index, terminal_uuid) {
        addTerminalStatusToDashboard(panel_body_id,terminal_uuid);
    });
}
function addTerminalStatusToDashboard(panel_body_id,terminal_uuid) {
    var theUrl = '/rs/terminal/' + terminal_uuid + '/status';
    $.ajax({
        url: theUrl,
        type: 'GET',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            formatDashboardTerminalInfo(panel_body_id,data);
        }
    });
}
function formatDashboardTerminalInfo(panel_body_id,terminal_info) {
    var panel_id = '#' + panel_body_id;
    var content = '<div id="' + terminal_info.uuid + '">';
    content += '<p class="lead">' + terminal_info.description;

    if(terminal_info.type == 1){  // Sensor Type
        content += '</p>';
        content += '<p>Value: <b>' + terminal_info.status.value + '</b></p>';
    }

    if(terminal_info.type == 2){  // Relay Type
        if(terminal_info.status.status == 'on'){
            content += '<a class="turnTerminalOff" href="javascript:void()" data-t-id="' + terminal_info.uuid + '"><span class="pull-right "><i class="glyphicon glyphicon-remove-circle"></i></span></a></p>';
            content += '<p>Status: <b>ON</b></p>';
        } else {
            content += '<a class="turnTerminalOn" href="javascript:void()" data-t-id="' + terminal_info.uuid + '"><span class="pull-right "><i class="glyphicon glyphicon-off"></i></span></a></p>';
            content += '<p>Status: <b>OFF</b></p>';
        }
    }

    content += '</div>';
    $(panel_id).prepend(content);
}
function setTerminalOn(terminal_uuid) {
    setTerminal(terminal_uuid,'on');
}
function setTerminalOff(terminal_uuid) {
    setTerminal(terminal_uuid,'off');
}
function setTerminal(terminal_uuid, cmd) {
    var div_id = '#' + terminal_uuid;
    $(div_id).fadeOut();
    var theUrl = '/rs/terminal/' + terminal_uuid + '/' + cmd;
    $.ajax({
        url: theUrl,
        type: 'POST',
        data: {},
        dataType: 'json',
        complete: function(response, status, xhr){
            var data = jQuery.parseJSON(response.responseText);
            reformatRelay(data);
        }
    })
}
function reformatRelay(relay) {
    var div_id = '#' + relay.uuid;
    var content = '<p class="lead">' + relay.description;
    if(relay.status.status == 'on'){
        content += '<a class="turnTerminalOff" href="javascript:void()" data-t-id="' + relay.uuid + '"><span class="pull-right "><i class="glyphicon glyphicon-remove-circle"></i></span></a></p>';
        content += '<p>Status: <b>ON</b></p>';
    } else {
        content += '<a class="turnTerminalOn" href="javascript:void()" data-t-id="' + relay.uuid + '"><span class="pull-right "><i class="glyphicon glyphicon-off"></i></span></a></p>';
        content += '<p>Status: <b>OFF</b></p>';
    }
    $(div_id).html(content);
    $(div_id).fadeIn();
}

/* ----------------------------------------------------------------------------------------------- */

$(document).on('click', '.panel-heading span.clickable', function (e) {
    var $this = $(this);
    if (!$this.hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideUp();
        $this.addClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
    } else {
        $this.parents('.panel').find('.panel-body').slideDown();
        $this.removeClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-plus').addClass('glyphicon-minus');
    }
});
$(document).on('click', '.panel div.clickable', function (e) {
    var $this = $(this);
    if (!$this.hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideUp();
        $this.addClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-minus').addClass('glyphicon-plus');
    } else {
        $this.parents('.panel').find('.panel-body').slideDown();
        $this.removeClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-plus').addClass('glyphicon-minus');
    }
});