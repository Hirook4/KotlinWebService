
<?php

require_once '../includes/DbOperation.php';

$response = array();

if (isset($_GET['op'])) {

	switch ($_GET['op']) {

			//http://localhost/projetos/webService/view/?op=insert
		case 'insert':
			if (isset($_POST['nome']) && isset($_POST['genero'])) {
				$db = new DbOperation();
				if ($db->insert($_POST['nome'], $_POST['genero'])) {
					$response['error'] = false;
					$response['message'] = 'Filme Adicionado Com Sucesso';
				} else {
					$response['error'] = true;
					$response['message'] = 'Erro ao Adicionar Filme';
				}
			} else {
				$response['error'] = true;
				$response['message'] = 'Parametros Invalidos';
			}
			break;

			//http://localhost/projetos/webService/view/?op=select
		case 'select':
			$db = new DbOperation();
			$filme = $db->select();
			if (count($filme) <= 0) {
				$response['error'] = true;
				$response['message'] = 'Banco de Dados Vazio!';
			} else {
				$response['error'] = false;
				$response['filme'] = $filme;
			}
			break;

		default:
			$response['error'] = true;
			$response['message'] = 'Nenhuma Operação Disponivel';
	}
} else {
	$response['error'] = false;
	$response['message'] = 'Requisição Invalida';
}

echo json_encode($response);
