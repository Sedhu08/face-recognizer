import cv2

# Load the pre-trained Haar Cascade for face detection
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

# Function to detect faces in an image
def detect_faces(image_path):
    # Read the image
    img = cv2.imread(image_path)
    
    # Convert the image to grayscale (necessary for Haar Cascade)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    
    # Detect faces in the image
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
    
    # Draw rectangles around detected faces
    for (x, y, w, h) in faces:
        cv2.rectangle(img, (x, y), (x+w, y+h), (255, 0, 0), 2)
    
    # Display the result
    cv2.imshow('Face Detection', img)
    
    # Wait for a key press and then close the image window
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# Example usage:
detect_faces(r'C:\Users\mihir\OneDrive\Desktop\project\istockphoto-1413873774-612x612.jpg')
