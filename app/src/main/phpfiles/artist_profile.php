        <?php
        $conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {

            $artistname = $_POST['artistname'];
            

            $sql = "SELECT * FROM artist_registration WHERE artistname='$artistname'";

            $response = mysqli_query($conn, $sql);

            $result = array();
            $result['profile'] = array();

            if (mysqli_num_rows($response) === 1) {

                $row = mysqli_fetch_assoc($response);

                

                    $index['AID'] = $row['AID'];
                    $AID=$row['AID'];
                    $index['artistname'] = $row['artistname'];
                    $index['fullname'] = $row['fullname'];
                    $index['email'] = $row['email'];
                    $index['phNo'] = $row['phNo'];

                    $followquery="SELECT COUNT(*) AS followcount FROM follow_artists WHERE AID='$AID'";
                    $result1 = mysqli_query($conn, $followquery);
                    $response1=mysqli_fetch_assoc($result1);
                    $index['followcount']=$response1['followcount'];

                    $uploadquery="SELECT COUNT(*) AS uploadcount FROM song_artists WHERE AID='$AID'";
                    $result2 = mysqli_query($conn, $uploadquery);
                    $response2=mysqli_fetch_assoc($result2);
                    $index['uploadcount']=$response2['uploadcount'];

                    array_push($result['profile'], $index);

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