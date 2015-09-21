function query() {
    $("#status").text("querying");
    $.ajax({
        type: "GET",
        url: "rest/service/greetings",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        $("#status").text("found " + data.length + " items");
        $("#list").text(JSON.stringify(data, null, 4));
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
        $("#list").text("");
    });
}

function add() {
    $("#status").text("adding");
    var greeting = {
        name: $("#add-name").val(),
        text: $("#add-text").val()
    };
    $.ajax({
        type: "POST",
        url: "rest/service/greetings",
        data: JSON.stringify(greeting),
        contentType: "application/json; charset=UTF-8" })
    .done(function(data, textStatus, jqXHR) {
        $("#add-name").val("");
        $("#add-text").val("");
        $("#status").text("added");
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
    });
}

function del() {
    $("#status").text("deleting");
    $.ajax({
        type: "DELETE",
        url: "rest/service/greeting/" + $("#del-id").val() })
    .done(function(data, textStatus, jqXHR) {
        $("#del-id").val("");
        $("#status").text("deleted");
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
    });
}

$(document).ready(function() {
    $("#query-button").click(query);
    $("#add-button").click(add);
    $("#del-button").click(del);
    $("#status").text("ready");
    query();
});
