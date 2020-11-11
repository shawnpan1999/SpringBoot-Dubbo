function modelFill(userId, productId, productName) {
    document.getElementById("orderCreateUserId").setAttribute("value", userId);
    document.getElementById("orderCreateProductId").setAttribute("value", productId);
    document.getElementById("orderCreateProductName").setAttribute("value", productName);
}

function orderCreate() {
    var userId = document.getElementById("orderCreateUserId").value;
    var productId = document.getElementById("orderCreateProductId").value;
    var amount = document.getElementById("orderCreateAmount").value;
    $.ajax({
        type : "post",
        url : "/order/create",
        dataType: "json",
        data : {
            userId: userId,
            productId: productId,
            amount: amount,
        },
        success : function(result){
            if (result.code == 0) {
                bs4pop.notice("创建成功, 订单ID: " + result.data.order.id, {type: "success"});
                refreshOrders(userId);
            } else {
                bs4pop.notice(result.msg, {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function refreshOrders(userId) {
    $.ajax({
        type : "post",
        url : "/order/list",
        dataType: "json",
        data : {
            userId: userId,
        },
        success : function(result){
            if (result.code == 0) {
                var orders = result.data.orders;
                document.getElementById("orderTableBody").innerHTML="";
                var tbody = document.getElementById("orderTableBody");
                for (var x in orders) {
                    var tr = document.createElement("tr");
                    tbody.appendChild(tr);
                    tr.id = "orderLine" + x;
                    setOrderLineByBid(tr, orders[x], x);
                }
            } else {
                bs4pop.notice("拉取订单列表失败", {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function orderDelete(oid, bid) {
    disableOrderButtons(bid);
    $.ajax({
        type : "post",
        url : "/order/delete",
        dataType: "json",
        data : {
            id: oid,
        },
        success : function(result){
            if (result.code == 0) {
                var t = document.getElementById("orderLine"+bid);
                t.remove();
                bs4pop.notice("删除成功", {type: "success"});
            } else {
                bs4pop.notice(result.msg, {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function orderPay(oid, bid) {
    disableOrderButtons(bid);
    $.ajax({
        type : "post",
        url : "/order/pay",
        dataType: "json",
        data : {
            id: oid,
        },
        success : function(result){
            if (result.code == 0) {
                setOrderLineByBid(document.getElementById("orderLine"+bid), result.data.order, bid);
                bs4pop.notice("付款成功", {type: "success"});
            } else {
                bs4pop.notice(result.msg, {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function orderFinish(oid, bid) {
    disableOrderButtons(bid);
    $.ajax({
        type : "post",
        url : "/order/finish",
        dataType: "json",
        data : {
            id: oid,
        },
        success : function(result){
            if (result.code == 0) {
                setOrderLineByBid(document.getElementById("orderLine"+bid), result.data.order, bid);
                bs4pop.notice("确认收货成功", {type: "success"});
            } else {
                bs4pop.notice(result.msg, {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function orderCancel(oid, bid) {
    disableOrderButtons(bid);
    $.ajax({
        type : "post",
        url : "/order/cancel",
        dataType: "json",
        data : {
            id: oid,
        },
        success : function(result){
            if (result.code == 0) {
                setOrderLineByBid(document.getElementById("orderLine"+bid), result.data.order, bid);
                bs4pop.notice("订单取消成功", {type: "success"});
            } else {
                bs4pop.notice(result.msg, {type: "danger"});
            }
        },
        error : function(result){
            bs4pop.notice("未知错误", {type: "danger"});
        }
    });
}

function setOrderLineByBid(orderLine, order, bid) {
    orderLine.innerHTML =
        "<td><div>"  + order.id            + "</div> </td>\n" +
        "<td><div> " + order.productName   + "</div> </td>\n" +
        "<td><div> " + order.unitPrice     + "</div> </td>\n" +
        "<td><div> " + order.amount        + "</div> </td>\n" +
        "<td><div>"  + order.totalPrice    + "</div> </td>\n" +
        "<td><div> " + order.createDate    + "</div> </td>\n" +
        "<td><div>"  + order.statusName    + "</div> </td>\n" +
        "<td id=\"buttonset" + bid + "\"" + "</td>\n";
    changeButtonsByStatus(document.getElementById("buttonset" + bid), order.id, bid, order.status);
}

function changeButtonsByStatus(buttonSet, oid, bid, status) {
    if (status == '-1') {
        buttonSet.innerHTML = "<div>\n" +
            "<button type=\"button\" class=\"btn btn-outline-danger btn-sm\" id=\"delete"+bid+"\"" +
            "    onclick=\"orderDelete('"+oid+"','"+bid+"')\">删除订单</button>\n" +
            "</div>"
    }
    if (status == '0') {
        buttonSet.innerHTML = "<div>\n" +
            "<button type=\"button\" class=\"btn btn-outline-info btn-sm\" id=\"pay"+bid+"\"" +
            "    onclick=\"orderPay('"+oid+"','"+bid+"')\">付款</button>\n" +
            "<button type=\"button\" class=\"btn btn-outline-danger btn-sm\" id=\"delete"+bid+"\"" +
            "    onclick=\"orderCancel('"+oid+"','"+bid+"')\">取消订单</button>\n" +
            "</div>"
    }
    if (status == '1') {
        buttonSet.innerHTML = "<div>\n" +
            "<button type=\"button\" class=\"btn btn-outline-info btn-sm\" id=\"finish"+bid+"\"" +
            "    onclick=\"orderFinish('"+oid+"','"+bid+"')\">确认收货</button>\n" +
            "</div>"
    }
    if (status == '2') {
        buttonSet.innerHTML = "<div>\n" +
            "<button type=\"button\" class=\"btn btn-outline-danger btn-sm\" id=\"delete"+bid+"\"" +
            "    onclick=\"orderDelete('"+oid+"','"+bid+"')\">删除订单</button>\n" +
            "</div>"
    }
}

function disableOrderButtons(bid) {
    let payButton = document.getElementById("pay" + bid);
    let finishButton = document.getElementById("finish" + bid);
    let cancelButton = document.getElementById("cancel" + bid);
    let deleteButton = document.getElementById("delete" + bid);
    if (payButton != null) {
        payButton.disabled=true;
    }
    if (finishButton != null) {
        finishButton.disabled=true;
    }
    if (cancelButton != null) {
        cancelButton.disabled=true;
    }
    if (deleteButton != null) {
        deleteButton.disabled=true;
    }
}