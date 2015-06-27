<?php
class API {
	public function returnSimpleValue() {
		return 1024;
	}

	public function returnArray() {
		return array(1, 2, 3);
	}

	public function returnMixed() {
		return array(
    		'id' => uniqid(),
    		'time' => time()
		);
	}

	public function echoRequest($param1, $param2) {
		return array(
			'param1' => $param1,
			'param2' => $param2
		);
	}

	public function throwException() {
		throw new Exception("Hello world!");
	}

	public function responseTimeout() {
		sleep(6);
	}
}

$service = new Yar_Server(new API());
$service->handle();
/* EOF */