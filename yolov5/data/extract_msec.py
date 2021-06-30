from pathlib import Path
import cv2
import argparse
from tqdm import tqdm

a = argparse.ArgumentParser()
a.add_argument("--pathIn", help="path to video folder")
a.add_argument("--pathOut", help="path to images folder")

count = 0


def extractImages(pathIn, pathOut):
    global count
    print("start", count)
    vidcap = cv2.VideoCapture(str(pathIn))
    success, image = vidcap.read()
    success = True
    fps = vidcap.get(cv2.CAP_PROP_FPS)  # OpenCV2 version 2 used "CV_CAP_PROP_FPS"
    frame_count = int(vidcap.get(cv2.CAP_PROP_FRAME_COUNT))
    duration = int(frame_count // fps)
    for i in tqdm(range(duration)):
        vidcap.set(cv2.CAP_PROP_POS_MSEC, (i * 1000))  # added this line

        success, image = vidcap.read()
        if success:
            cv2.imwrite(
                str(pathOut / f"{count:05}.jpg"), image
            )  # save frame as JPEG file
            count = count + 1
    print("end", count)


if __name__ == "__main__":
    data_dir = Path("./raw_videos/")
    save_dir = Path("./images/")
    data_ls = data_dir.glob("*.mp4")
    data = []
    for video_path in data_ls:
        video_name = video_path.stem
        image_dir = save_dir  # / video_name
        image_dir.mkdir(parents=True, exist_ok=True)
        extractImages(pathIn=video_path, pathOut=image_dir)
