<?php

$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    
    $songname = $_POST['songname'];
    $songurl = $_POST['songurl'];
    $genre = $_POST['genre'];
    $language = $_POST['language'];
    $album = $_POST['album'];
    $AID=$_POST['AID'];

  

    $sql = "INSERT INTO song  VALUES (NULL,'$songname','$songurl','$genre','$language','$album')";
     


    if (mysqli_query($conn, $sql)) {

        $songsql = "SELECT SID FROM song WHERE songurl = '$songurl'";

        $response = mysqli_query($conn, $songsql);

        $row = mysqli_fetch_object($response);

        $sql1 = "INSERT INTO song_artists  VALUES ($row->SID,$AID)";

        if (mysqli_query($conn, $sql1)) {

            $result["success"] = "1";
            $result["message"] = "success";

            echo json_encode($result);
            mysqli_close($conn);

        }

    } else {

        $result["success"] = "0";
        $result["message"] = "Song uploading failed";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>