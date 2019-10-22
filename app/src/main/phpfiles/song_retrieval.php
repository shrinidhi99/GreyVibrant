<?php
$conn = mysqli_connect("localhost","id11221849_sabios","Sayan@99","id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD']=='POST') {

    $AID = $_POST['AID'];

    $sql = "SELECT S.songname, S.genre, S.language, S.album from song S join song_artists A on S.SID = A.SID where A.AID = '$AID'";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['album'] = array();

    if ( mysqli_num_rows($response) > 1 ) {

        while($row = mysqli_fetch_assoc($response))
        {
            $index['songname']=$row['songname'];
            $index['genre']=$row['genre'];
            $index['language']=$row['language'];
            $index['album']=$row['album'];
            array_push($result['album'], $index);
        }

        $result['success'] = "1";
        $result['message'] = "success";
        echo json_encode($result);

        mysqli_close($conn);

    }else {

        $result['success'] = "0";
        $result['message'] = "error";
        echo json_encode($result);

        mysqli_close($conn);

    }

}

?>