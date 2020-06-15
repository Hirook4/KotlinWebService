
<?php

class DbOperation
{
	private $con;

	function __construct()
	{
		require_once dirname(__FILE__) . '/DbConnect.php';
		$db = new DbConnect();
		$this->con = $db->connect();
	}

	public function insert($nome, $genero)
	{
		$sql = "insert into filme (nome, genero) values (?, ?)";
		$stmt = $this->con->prepare($sql);
		$stmt->bind_param("ss", $nome, $genero);
		if ($stmt->execute())
			return true;
		return false;
	}

	public function select()
	{
		$sql = "select id, nome, genero from filme";
		$stmt = $this->con->prepare($sql);
		$stmt->execute();
		$stmt->bind_result($id, $nome, $genero);
		$filmes = array();

		while ($stmt->fetch()) {
			$temp = array();
			$temp['id'] = $id;
			$temp['nome'] = $nome;
			$temp['genero'] = $genero;
			array_push($filmes, $temp);
		}
		return $filmes;
	}
}
