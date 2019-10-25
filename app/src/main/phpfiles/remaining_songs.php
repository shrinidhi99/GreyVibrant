<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $UID = $_POST['UID'];
    // logic
    // retrieving all unfollowed artists by that user
    // get all the songs that are uploaded by those artists
    $sql0 = "SELECT A.AID , A.artistname FROM artist_registration A WHERE A.AID NOT IN (SELECT F.AID FROM follow_artists F WHERE F.UID=$UID)";
    $result = array();
    $result['songdetail'] = array();
    $response0 = mysqli_query($conn, $sql0);

    while ($row0 = mysqli_fetch_assoc($response0)) {
        $index['AID'] = $row0['AID'];
        $AIDtemp = $row0['AID'];
        $index['artistname'] = $row0['artistname'];
        $sql1 = "SELECT * FROM song S, song_artists A WHERE A.AID=F.AID and A.AID = $AIDtemp";
        $response1 = mysqli_query($conn, $sql1);

        while ($row1 = mysqli_fetch_assoc($response1)) {
            $index['SID'] = $row1['SID'];
            $index['songname'] = $row1['songname'];
            $index['songurl'] = $row1['songurl'];
            $index['genre'] = $row1['genre'];
            $index['language'] = $row1['language'];
            $index['album'] = $row1['album'];

            array_push($result['songdetail'], $index);
        }
    }


    if (mysqli_num_rows($response1) > 0) {


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
