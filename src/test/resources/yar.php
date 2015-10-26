<?php
class API {
	public function returnSimpleValue() {
		return 1024;
	}
	
	public function testParams($par) {
		return $par;
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

	public function returnComplexObject() {
		return array(
			'list' => array(
				array(
					'id' => 1,
					'name' => 'user1'
				),
				array(
					'id' => 2,
					'name' => 'user2'
				)
			) 
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