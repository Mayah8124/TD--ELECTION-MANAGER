select count(id) as total_votes from vote ;

select vote_type, count(vote.id) as total_vote from vote
group by vote_type
order by vote_type asc;

select
    c.name,
    (
        select count(v.id)
        from vote v
        where v.candidate_id = c.id
          and v.vote_type = 'VALID'
    ) as total_valid_votes
from candidate c
order by c.id;

select count((case when v.vote_type = 'VALID' then 1 end)) as valid_count,
       count ((case when v.vote_type = 'BLANK' then 1 end)) as blank_count,
       count((case when v.vote_type = 'NULL' then 1 end)) as null_count
from vote v;

select 100 * count(distinct voter_id) / (select count(id) from voter) || '%' as participation_rate
from vote;

select c.name as candidate_name,
       count(v.id) as valid_vote_count
from candidate c
         join vote v on v.candidate_id = c.id
where v.vote_type = 'VALID'
group by c.id, c.name
order by valid_vote_count desc
    limit 1;