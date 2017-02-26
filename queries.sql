use uetchat2;
-- kiểm tra xem trạng thái user đã vào chat, nhưng vẫn chưa được ghép trong bảng chat
select u.user_id, u.updated_at, c.lhs_fb_id, u.in_chat
from user_java u
left join chat_sessions c on u.user_id = c.lhs_fb_id
having c.lhs_fb_id is null;

-- kiểm tra số lượng trai gái
select u.gender, count(*)
from user_java
group by u.gender;

