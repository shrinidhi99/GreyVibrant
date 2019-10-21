<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $username = $_POST['username'];
    

    $sql = "SELECT * FROM user_registration WHERE username='$username'";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['profile'] = array();

    if (mysqli_num_rows($response) === 1) {

        $row = mysqli_fetch_assoc($response);

        

            $index['UID'] = $row['UID'];
            $index['username'] = $row['username'];
            $index['fullname'] = $row['fullname'];
            $index['email'] = $row['email'];
            $index['phNo'] = $row['phNo'];


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