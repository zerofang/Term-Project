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
$host = 'localhost';//填服务器名
$user = '';//填数据库用户名
$pass = '';//填数据库密码
$name = '';//填数据库名字

require("database.php");
function getConstraints($table, $columnName)
{
    $getConstraints = 'SELECT cols.TABLE_NAME AS INITIAL_TABLE_NAME, cols.COLUMN_NAME AS INITIAL_COLUMN_NAME, cols.ORDINAL_POSITION,
  cols.COLUMN_DEFAULT, cols.IS_NULLABLE, cols.DATA_TYPE,
  cols.CHARACTER_MAXIMUM_LENGTH, cols.CHARACTER_OCTET_LENGTH,
  cols.NUMERIC_PRECISION, cols.NUMERIC_SCALE,
  cols.COLUMN_TYPE, cols.COLUMN_KEY, cols.EXTRA,
  cols.COLUMN_COMMENT, refs.REFERENCED_TABLE_NAME, refs.REFERENCED_COLUMN_NAME,
  cRefs.UPDATE_RULE, cRefs.DELETE_RULE,
  links.TABLE_NAME, links.COLUMN_NAME,
  cLinks.UPDATE_RULE, cLinks.DELETE_RULE
  FROM INFORMATION_SCHEMA.`COLUMNS` as cols
  LEFT JOIN INFORMATION_SCHEMA.`KEY_COLUMN_USAGE` AS refs
  ON refs.TABLE_SCHEMA=cols.TABLE_SCHEMA
  AND refs.REFERENCED_TABLE_SCHEMA=cols.TABLE_SCHEMA
  AND refs.TABLE_NAME=cols.TABLE_NAME
  AND refs.COLUMN_NAME=cols.COLUMN_NAME
  LEFT JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS cRefs
  ON cRefs.CONSTRAINT_SCHEMA=cols.TABLE_SCHEMA
  AND cRefs.CONSTRAINT_NAME=refs.CONSTRAINT_NAME
  LEFT JOIN INFORMATION_SCHEMA.`KEY_COLUMN_USAGE` AS links
  ON links.TABLE_SCHEMA=cols.TABLE_SCHEMA
  AND links.REFERENCED_TABLE_SCHEMA=cols.TABLE_SCHEMA
  AND links.REFERENCED_TABLE_NAME=cols.TABLE_NAME
  AND links.REFERENCED_COLUMN_NAME=cols.COLUMN_NAME
  LEFT JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS cLinks
  ON cLinks.CONSTRAINT_SCHEMA=cols.TABLE_SCHEMA
  AND cLinks.CONSTRAINT_NAME=links.CONSTRAINT_NAME
  WHERE cols.TABLE_SCHEMA= \'mydb\'
  AND cols.TABLE_NAME= ?
  AND cols.COLUMN_NAME= ?;';

    $pdo = Database::connect();
    $q1 = $pdo->prepare($getConstraints);
    $q1->execute(array($table,$columnName));
    $constraints = null;
    foreach ($q1->fetchAll() as $row) {
        if ($columnName==$row['INITIAL_COLUMN_NAME']) {
            $constraints = array(
              0 => $row['IS_NULLABLE'],
              1 => $row['COLUMN_TYPE'],
              2 => $row['COLUMN_KEY'],
              3 => $row['EXTRA'],
              4 => $row['REFERENCED_TABLE_NAME'],
              5 => $row['REFERENCED_COLUMN_NAME'],
            );
        }
    }
    return $constraints;
}

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
