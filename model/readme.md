# Data Scripts

## File Structure:
```
this folder
│   
└─── scripts
|      └─── data
|            # something
|      └─── training
|            # something
|      └─── prediction
|            # something
│   
└─── data
|      └─── raw
|            # something
|      └─── yolo
|            # something
│   
└─── tools
|       # something
│   
└─── modules
|       # yolov5 authors modules
|          
```
Bài toán chúng tôi thực hiện trên bộ dữ liệu tự xây dựng từ labelme, chính vì vậy
công đoạn chuyển đổi định dạng giữa labelme và yolo  là điều đầu tiên cần làm sau khi gán 
nhãn dữ liệu
## Preparing data:
1. Use ```./raw/extract_frame_all.py``` extract data from videos (every frames)
2. Use ```./raw/extract_frame_msc.py``` extract data from videos (every mileseconds)
3. Use ```convert-format``` to convert labelme outputs to YOLO format
