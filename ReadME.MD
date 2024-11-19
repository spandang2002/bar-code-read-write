**To Generate and Display the Barcode with Text:**
You can test the new barcode generation endpoint using a browser or a tool like Postman:

For example, navigate to:

http://localhost:8080/api/barcode/generate?text=yourTextHere
You can replace yourTextHere with any text you want to encode into the barcode. The generated QR code image will include the text "spandan" in the center with a white background for better visibility.

By following these steps, the generated QR code will have the text "spandan" visibly and centrally placed within the image.
http://localhost:8080/api/barcode/generate?text=SampleBarCode

**To Decode Bar Code**
```shell
curl -F "file=@/Users/user1/Downloads/code-gen-stu-1.png" http://localhost:8080/api/barcode/decode
```

