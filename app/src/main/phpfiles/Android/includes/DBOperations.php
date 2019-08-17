<?php
	class DBOperations{
		private $con;

		function __construct(){
			require_once dirname(__FILE__).'/DbConnect.php';
			$db = new DbConnect();

			$this->con = $db->connect();
		}

		/*
			CRUD -> C -> CREATE
		*/
		public function createUser($username, $pass, $email){
			if($this->isUserExist($username, $email))
			{
				return 0;
			}
			$password = md5($pass);
			$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES (NULL, ?, ?, ?);");
			$stmt->bind_param("sss",$username,$password,$email);
			if($stmt->execute()){
				return 1;
			}
			else
			{
				return 2;
			}
		}

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
			$stmt->bind_param("ss",$username,$email);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}
	}