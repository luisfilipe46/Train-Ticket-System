<?php
use Cake\Utility\Xml;
$xml = Xml::fromArray(['response' => $message]);
echo $xml->asXML();
?>
