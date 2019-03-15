<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $id_user = $_POST['id_user'];
    $password = $_POST['password'];

    require_once 'koneksidb.php';

    $sql = "SELECT * FROM tb_user WHERE id_user='$id_user' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {
        
        $row = mysqli_fetch_assoc($response);

        if ( password_verify($password, $row['password']) ) {
            
            $index['nama_user'] = $row['nama_user'];
            $index['id_user'] = $row['id_user'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }

}

?>