<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>ServletHomework</title>
        <script type="text/javascript">
            function cmdGet() {
                var xHttpReq = new XMLHttpRequest();
                var args = "?cmd=true&idx=" + document.getElementById("idx").value;
                xHttpReq.onreadystatechange = function() {
                    if(this.readyState == 4 && this.status == 200) {
                        //document.getElementById("str").value = xHttpReq.responseText;
                        document.getElementById("collection").reload(true);
                        //document.location.reload(true);
                    }
                };
                xHttpReq.open("GET", args, true);
                xHttpReq.send();
            };

            function cmdPost() {
                var xHttpReq = new XMLHttpRequest();
                var data = "str=" + document.getElementById("str").value;
                xHttpReq.onreadystatechange = function() {
                    if(this.readyState == 4 && this.status == 200) {
                        document.getElementById("str").value = xHttpReq.responseText;
                        document.getElementById("idx").max++;
                        document.getElementById("idx").value = document.getElementById("idx").max;
                        document.getElementById("idx").disabled = false;
                        document.getElementById("cmd_get").disabled = false;
                        document.getElementById("cmd_put").disabled = false;
                        document.getElementById("cmd_del").disabled = false;
                    }
                };
                xHttpReq.open("POST", "", true);
                xHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
                xHttpReq.send(data);
            };

            function cmdPut() {
                var xHttpReq = new XMLHttpRequest();
                var data = "str=" + document.getElementById("str").value + "&idx=" + document.getElementById("idx").value;
                xHttpReq.onreadystatechange = function() {
                    if(this.readyState == 4 && this.status == 200) {
                        document.getElementById("str").value = xHttpReq.responseText;
                    }
                };
                xHttpReq.open("PUT", "", true);
                xHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
                xHttpReq.send(data);
            };

            function cmdDelete() {
                var xHttpReq = new XMLHttpRequest();
                var data = "idx=" + document.getElementById("idx").value;
                xHttpReq.onreadystatechange = function() {
                    if(this.readyState == 4 && this.status == 200) {
                        document.getElementById("str").value = "";
                        document.getElementById("idx").max--;
                        if(document.getElementById("idx").max < 0) {
                            document.getElementById("str").value = xHttpReq.responseText;
                            document.getElementById("idx").disabled = true;
                            document.getElementById("cmd_get").disabled = true;
                            document.getElementById("cmd_put").disabled = true;
                            document.getElementById("cmd_del").disabled = true;
                        }
                    }
                };
                xHttpReq.open("DELETE", "", true);
                xHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
                xHttpReq.send(data);
            };
        </script>
    </head>
    <body>
        <h4>Manipulations with collection of strings</h4>
        <div>
            <label for="idx">Enter the index</label>
            <input type="number" name="idx" id="idx" min="0" max="-1" disabled=true>
        </div>
        <div>
            <label for="str">Enter the string</label>
            <input type="text" name="str" id="str">
        </div>
        <p><button id="cmd_get"  onclick="cmdGet();"    disabled=true>Get (get string from list)</button></p>
        <p><button id="cmd_post" onclick="cmdPost();"                >Post (insert string in list)</button></p>
        <p><button id="cmd_put"  onclick="cmdPut();"    disabled=true>Put (rewrite string)</button></p>
        <p><button id="cmd_del"  onclick="cmdDelete();" disabled=true>Delete (delete string)</button></p>

        <div id="collection">
            <c:forEach var="item" items="${list}">
                <p>"${item}"</p>
            </c:forEach>
        </div>
    </body>
</html>
