<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $username = $_POST['username'];
    $nama = $_POST['nama'];
    $password = $_POST['password'];

    require_once 'koneksidb.php';

    $sql = "INSERT INTO admin VALUES ('$username', '$nama', '$password')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>