<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $UID = $_POST['UID'];
    $sql1 = "SELECT * FROM song S ,playlist_name P WHERE S.SID=P.SID AND P.UID=$UID";
    $response1 = mysqli_query($conn, $sql1);
    $result = array();
    $result['songdetail'] = array();

    while ($row1 = mysqli_fetch_assoc($response1)) {
        $index['SID'] = $row1['SID'];
        $SIDtemp = $row1['SID'];
        $index['songname'] = $row1['songname'];
        $index['songurl'] = $row1['songurl'];
        $index['genre'] = $row1['genre'];
        $index['language'] = $row1['language'];
        $index['album'] = $row1['album'];
        $sql2 = "SELECT A.artistname,A.AID from artist_registration A , song_artists S WHERE S.AID=A.AID AND S.SID=$SIDtemp";
        $response2 = mysqli_query($conn, $sql2);
        $row2 = mysqli_fetch_assoc($response2);
        $index['artistname'] = $row2['artistname'];
        $index['AID'] = $row2['AID'];
        array_push($result['songdetail'], $index);
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
?>