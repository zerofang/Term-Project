<?php

class Database
{
  private static $dbName = '' ;//数据库名
  private static $dbHost = 'localhost';//服务器地址
  private static $dbUsername = '';//用户名
  private static $dbUserPassword = '';//用户密码
  private static $cont  = null;
  public function __construct() {
    exit('Init function is not allowed');
  }

  public static function connect()
  {
     // One connection through whole application
       if ( null == self::$cont )
       {
        try
        {
          self::$cont =  new PDO( "mysql:host=".self::$dbHost.";"."dbname=".self::$dbName, self::$dbUsername, self::$dbUserPassword);
        }
        catch(PDOException $e)
        {
          die($e->getMessage());
        }
       }
       return self::$cont;
  }

  public static function connect_fordrop()
  {
     // One connection through whole application
       if ( null == self::$cont )
       {
        try
        {
          self::$cont =  new PDO( "mysql:host=".self::$dbHost.";", self::$dbUsername, self::$dbUserPassword);
        }
        catch(PDOException $e)
        {
          die($e->getMessage());
        }
       }
       return self::$cont;
  }

  public static function disconnect()
  {
    self::$cont = null;
  }
}
?>
