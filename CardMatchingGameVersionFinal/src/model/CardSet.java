package model ;

import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;

public class CardSet {
    private final CardSetType type; // เก็บประเภทของชุดการ์ด(ANIMALS, FRUITS) แบบค่าคงที่ (immutable)
    private final List<String> images; // ลิสต์ที่เอาไว้เก็บรายชื่อการ์ดทั้งหมดในชุดการ์ด

    //------ Constructor ------
    // สำหรับสร้างออบเจ็กต์ CardSet รับประเภทและรายการรูปภาพเข้ามา
    public CardSet(CardSetType type, List<String> images) {
        this.type = type; 
        // สร้าง ArrayList ใหม่แล้วคัดลอกข้อมูลจาก images เข้าไป เพื่อป้องกันไม่ให้ข้อมูลภายนอกถูกแก้ไขกระทบชุดการ์ดโดยตรง
        this.images = new ArrayList<>(images); 
    }

    // ------ Medthod ------
    // ดึงค่าประเภทของชุดการ์ดนี้
    public CardSetType getType() {
        return type;
    }

    // ดึงรายการรูปภาพทั้งหมดในชุดการ์ด
    public List<String> getImages() {
        // คืนค่าลิสต์ในรูปแบบที่อ่านได้อย่างเดียว ไม่สามารถเพิ่ม/ลบข้อมูลข้างนอกได้
        return Collections.unmodifiableList(images);
    }
}