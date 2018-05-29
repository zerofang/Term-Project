<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style type="text/css">
        table{
            margin: 0 auto;
        }
        table,th,td {
            border : 1px solid black;
            border-collapse: collapse;
        }
        th,td {
            padding: 5px;
        }
    </style>
</head>
<body>

    <h1>XMLHttpRequest</h1>

    <button type="button" onclick="loadDoc()">获取xml</button>
    <br><br>
    <table id="demo"></table>

<script>
function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      myFunction(this);
    }
  };
  xhttp.open("GET", "jisuan.xml", true);
  xhttp.send();
}
function myFunction(xml) {
  var i;
  var xmlDoc = xml.responseXML;
  var table="<tr><th>ID</th><th>STUID</th><th>NAME</th><th>SEX</th><th>DEPT</th><th>EMAIL</th></tr>"; 
  var x = xmlDoc.getElementsByTagName("record");
  for (i = 0; i <x.length; i++) {
    table += "<tr><td>" +
    x[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue +
    "</td><td>" +
    x[i].getElementsByTagName("STUID")[0].childNodes[0].nodeValue +
    "</td><td>" +
    x[i].getElementsByTagName("NAME")[0].childNodes[0].nodeValue +
    "</td><td>" +
    x[i].getElementsByTagName("SEX")[0].childNodes[0].nodeValue +
    "</td><td>" +
    x[i].getElementsByTagName("DEPT")[0].childNodes[0].nodeValue +
    "</td><td>" +
    x[i].getElementsByTagName("EMAIL")[0].childNodes[0].nodeValue +
    "</td></tr>";
  }
  document.getElementById("demo").innerHTML = table;
}
</script>
<?php
$host = 'localhost';
$user = 'your username';//此处替换为你自己的数据库用户名，密码，数据库名
$pass = 'your password';
$name = 'database name';



//connect
$link = mysqli_connect($host, $user, $pass);
mysqli_select_db($link, $name);

//get all the tables
$query = 'SHOW TABLES FROM '.$name;
$result = mysqli_query($link, $query) or die('cannot show tables');
if (mysqli_num_rows($result)) {
    //prep output
    $tab = "\t";
    $br = "\n";
    $xml = '<?xml version="1.0" encoding="UTF-8"?>'.$br;
    //for every table...
    while ($table = mysqli_fetch_row($result)) {
        //get the rows
        $query3 = 'SELECT * FROM '.$table[0];
        $records = mysqli_query($link, $query3) or die('cannot select from table: '.$table[0]);

        //stick the records
        $xml.= '<records>'.$br;
        while ($record = mysqli_fetch_assoc($records)) {
            $xml.= $tab.'<record>'.$br;
            foreach ($record as $key=>$value) {
                $xml.= $tab.$tab.'<'.$key.'>'.htmlspecialchars(stripslashes($value)).'</'.$key.'>'.$br;
            }
            $xml.= $tab.'</record>'.$br;
        }
        $xml.= '</records>'.$br;
    }
    
    echo 'Imported Successfully! Check your XML folder.';

    //save file
    $handle = fopen($name.'.xml', 'w+');
    fwrite($handle, $xml);
    fclose($handle);
}
?>
</body>
</html>
