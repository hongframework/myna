$('.chat_list').on("click", ".chat_item", function(){
    var username = $(this).attr("data-username");
    var userno = $(this).attr("data-userno");
    var avatar = $(this).find(".avatar .img").attr("src");
    var nickname = $(this).find(".nickname").text();
    var charArea = "chat-area-" + userno;

    $(".chatRoom").hide();
    if($("#" + charArea).size() > 0) {
        $("#" + charArea).show();
    }else {
        var $chatArea = $("#chat-area").clone();
        $chatArea.attr("id", charArea);
        $chatArea.attr("avatar-src", avatar);
        $chatArea.attr("userno", userno);
        $chatArea.attr("nickname", nickname);
        $chatArea.attr("id", charArea);
        $chatArea.appendTo($("#chat-area").parent());
        $("#" + charArea + " .title_wrap .title a").text("与 \"" + username + "\" 聊天");
        $("#" + charArea + " .box_ft").show();
        // addYouUtterance($("#" + charArea), "欢迎");
        $("#" + charArea).show();
        var url = "/robot/conn/" + userno + ".json";
        $.ajax({
            url: url,
            data: {},
            type: 'post',
            dataType: 'json',
            success: function(data){
                if(data.resultCode == 0) {
                    var utterances = data.data.utterance;
                    var sessionId = data.data.sessionId;
                    $chatArea.attr("sessionId", sessionId);
                    for(var i in utterances) {
                        addYouUtterance($("#" + charArea), utterances[i]);
                    }
                    setInterval("pullMessage()",500)
                }else {
                    alert(data.resultMessage);
                }
                $chatArea.find("div[jquery-scrollbar]:last").css("height","490px");
            }
        });
    }



});
$('.box_ft').on("click", ".btn_send", function(){
    alert("1");
    var utterance = $(this).parent().parent().find("#editArea").text();
    var $chatRoom = $(this).parents(".chatRoom:first");

});

function send(){
    var $chatRoom = $(".chatRoom:visible");
    var utterance = $chatRoom.find("#editArea").text();
    $chatRoom.find("#editArea").text("");
    addMeUtterance($chatRoom, utterance);
}
function pullMessage(){
    var _chatRoom = $(".chatRoom:visible");
    var userno = _chatRoom.attr("userno");
    var url = "/robot/heartbeat/" + userno + "/" + _chatRoom.attr("sessionId") + ".json";
    $.ajax({
        url: url,
        data: {},
        type: 'post',
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 0) {
                var utterances = data.data.utterance;
                var sessionId = data.data.sessionId;
                if(_chatRoom.attr("sessionId") != sessionId) {
                }else{
                    for(var i in utterances) {
                        addYouUtterance(_chatRoom, utterances[i]);
                    }
                }
            }
        }
    });
}

$(document).keyup(function(event){
    if(event.keyCode ==13){
        send();
    }
});

function addYouUtterance(_chatRoom, _content, _time){
    var meItem = $("#chat-item-you-template").clone();
    var avatar = _chatRoom.attr("avatar-src");
    var nickname = _chatRoom.attr("nickname");
    meItem.find("img.avatar").attr("src", avatar);
    meItem.find(".nickname").text(nickname);
    meItem.find(".plain .js_message_plain").text(_content);
    meItem.find(".message-time").text(_time);
    meItem.show();
    meItem.appendTo(_chatRoom.find(".scroll-wrapper div[data-height-calc]"));
    focusLastMessage(_chatRoom.find(".scroll-wrapper div[data-height-calc]"));
}

function disFocusLastMessage(){
    // var $chatRoom = $(".chatRoom:visible");
    // $chatRoom.find(".scroll-wrapper div[data-height-calc]").css("margin-top","38px");
}

// $('div.scroll-wrapper').on("scroll", ".scroll-content", function(){
//     $(this).children().first().css("margin-top","0px");
// });
function focusLastMessage(_$contentBox){
    var containerHeight = $("[jquery-scrollbar]:last").parent().css("height");
    $("[jquery-scrollbar]:last").css("height", containerHeight);
    containerHeight = containerHeight.substring(0, containerHeight.length -2);

    var height = _$contentBox.css("height");
    height = height.substring(0, height.length -2) - containerHeight;
    _$contentBox.parent().scrollTop(height);
}

function addMeUtterance(_chatRoom, _content, _time){
    var meItem = $("#chat-item-me-template").clone();
    var avatar = $(".header .avatar img").attr("src");
    var userno = _chatRoom.attr("userno");
    // meItem.find("img.avatar").attr("src", "img/lufei.png");
    meItem.find("img.avatar").attr("src", avatar);
    meItem.find(".plain .js_message_plain").text(_content);
    meItem.find(".message-time").text(_time);

    meItem.show();
    meItem.appendTo(_chatRoom.find(".scroll-wrapper div[data-height-calc]"));
    focusLastMessage(_chatRoom.find(".scroll-wrapper div[data-height-calc]"));
    var url = "/robot/chat/" + userno + "/" + _chatRoom.attr("sessionId") + ".json";
    $.ajax({
        url: url,
        data: {"utterance":_content },
        type: 'post',
        dataType: 'json',
        success: function(data){
            if(data.resultCode == 0) {
                var utterances = data.data.utterance;
                var sessionId = data.data.sessionId;
                if(_chatRoom.attr("sessionId") != sessionId) {
                    alert("session 失效 ！");
                }else{
                    for(var i in utterances) {
                        addYouUtterance(_chatRoom, utterances[i]);
                    }
                }
            }else {
                alert(data.resultMessage);
            }
        }
    });
}

// window.onbeforeunload = function(){
//     var _chatRoom = $(".chatRoom:visible");
//     var userno = _chatRoom.attr("userno");
//     var url = "/save/disc/" + userno + "/" + _chatRoom.attr("sessionId") + ".json";
//     $.ajax({
//         url: url,
//         type: 'post',
//         dataType: 'json',
//         success: function(data){
//         }
//     });
// }


function saveChat(){
    var _chatRoom = $(".chatRoom:visible");
    var userno = _chatRoom.attr("userno");
    if(userno) {
        var url = "/robot/save/" + userno + "/" + _chatRoom.attr("sessionId") + ".json";
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            success: function(data){
            }
        });
    }
}

window.addEventListener("beforeunload", function(event) {
    saveChat();
    console.info("save ....");
    event.returnValue = "是否保存聊天记录?";
});

window.onunload = function(){
    var _chatRoom = $(".chatRoom:visible");
    var userno = _chatRoom.attr("userno");
    var url = "/robot/disc/" + userno + "/" + _chatRoom.attr("sessionId") + ".json";
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function(data){
        }
    });
}