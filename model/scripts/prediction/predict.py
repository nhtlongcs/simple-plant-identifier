import argparse
from genericpath import exists
import os
from glob import glob
from PIL import Image
from numpy.lib.npyio import save
import torch
from pathlib import Path
import sys
from tqdm import tqdm


def json_predict(model, path: str):
    img = Image.open(path)

    results = model(img, size=640)
    data = results.pandas().xyxy[0].to_json(orient="records")
    return data


if __name__ == "__main__":

    model = torch.hub.load(
        "./module", "custom", path="./yolov5s.pt", source="local",
    ).autoshape()  # force_reload = recache latest code
    model.eval()

    parser = argparse.ArgumentParser(description="export json script")
    parser.add_argument("--inp-folder", default="./data/samples/input")
    parser.add_argument("--out-folder", default="./data/samples/output")
    args = parser.parse_args()

    img_ls = glob(os.path.join(args.inp_folder, "*"))
    save_dir = Path(args.out_folder)
    save_dir.mkdir(parents=True, exist_ok=True)
    for item in tqdm(img_ls):
        out_filename = save_dir / (Path(item).stem + ".json")
        data = json_predict(model, item)
        with open(out_filename, "a") as f:
            f.write(data)

